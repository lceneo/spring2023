package com.example.spring2023.Application.Services;
import com.example.spring2023.Domain.DTO.RequestDTO.FilmFiltersRequestDTO;
import com.example.spring2023.Domain.DTO.RequestDTO.FilmRequestDTO;
import com.example.spring2023.Domain.models.Actor;
import com.example.spring2023.Domain.models.Film;
import com.example.spring2023.DAL.repositories.IActorRepository;
import com.example.spring2023.DAL.repositories.IFilmActorRepository;
import com.example.spring2023.DAL.repositories.IFilmRepository;
import com.example.spring2023.Domain.models.User;
import com.example.spring2023.Domain.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class FilmService implements IFilmService {

    private final IFilmRepository filmRepository;
    private final IActorRepository actorRepository;
    private final IFilmActorRepository filmActorRepository;

    private final IActorService actorService;

    private final IUserPreferencesService userPreferencesService;

    private final IUserService userService;
    private final IMailSender mailSender;

    @Autowired
    public FilmService(
            IFilmRepository filmRepository,
            IActorRepository actorRepository,
            IFilmActorRepository filmActorRepository,
            IActorService actorService,
            IUserPreferencesService userPreferencesService,
            IUserService userService,
            IMailSender mailSender) {
        this.filmRepository = filmRepository;
        this.actorRepository = actorRepository;
        this.filmActorRepository = filmActorRepository;
        this.actorService = actorService;
        this.userPreferencesService = userPreferencesService;
        this.userService = userService;
        this.mailSender = mailSender;
    }

    public SimpleEntry<Film, List<Actor>> createOrUpdateFilm(FilmRequestDTO film) {
        Film updatedOrCreatedFilm;

        if (film.getId() != null)
            updatedOrCreatedFilm = this.filmRepository.update(film.getId(), film.getName(), film.getGenre(), film.getReleaseYear());
        else
            updatedOrCreatedFilm = this.filmRepository.saveFilm(film.getName(), film.getGenre(), film.getReleaseYear());

        if (film.getActorsID() != null && !film.getActorsID().isEmpty()) {
            film.getActorsID()
                    .forEach(actorID -> this.filmActorRepository.insertData(actorID, updatedOrCreatedFilm.getId()));
            updatedOrCreatedFilm.setActorsID(film.getActorsID());
            if (film.getId() == null) { this.sendNewFilmWithActorEmail(updatedOrCreatedFilm); }
        }
        return new SimpleEntry<>(updatedOrCreatedFilm, this.actorRepository.findAll());
    }
    public SimpleEntry<List<Film>, List<Actor>> getFilms(FilmFiltersRequestDTO filters, Optional<User> user) {
        var films = this.filmRepository.findWithFilters(filters.getSearchStr(), filters.getGenre(), filters.getReleaseYear(),
                filters.getSkip(), filters.getTake());

        films.forEach(film -> {
             var filmActors = this.filmActorRepository.getAllFilmActors(film.getId());
             film.setActorsID(filmActors);
        });

        if (user.isPresent()) {
            var preferences = this.userPreferencesService.getMyPreferences(user.get());
            var favoriteFilms = preferences
                    .getFavoriteFilmsIDs()
                    .stream()
                    .map(id -> this.getFilmByID(id).getKey())
                    .toList();

            var favoriteActorsIDs = preferences.getFavoriteActorsIDs();
            films.sort((firstFilm, secondFilm) -> this.compareFilms(firstFilm, secondFilm, favoriteFilms, favoriteActorsIDs));
        }
        return new SimpleEntry<>(films, this.actorRepository.findAll());
    }

    public SimpleEntry<Film, List<Actor>> getFilmByID(Long id) {
        var film = this.filmRepository.findById(id).orElse(null);
        if (film == null) {return null;}
            var filmActors = this.filmActorRepository.getAllFilmActors(film.getId());
            film.setActorsID(filmActors);
        return new SimpleEntry<>(film, this.actorRepository.findAll());
    }

    public void deleteFilm(Long id) {
        this.filmRepository.deleteById(id);
        this.filmActorRepository.deleteFilmActors(id);
    }

    private void sendNewFilmWithActorEmail(Film film) {
        var users = this.userService.getUsers();
        if (users.isEmpty()) { return; }
        users.forEach(user -> {
            var actorsFromPreferences = film.getActorsID()
                    .stream()
                    .filter(actorID -> this.userPreferencesService
                            .getUserPreferences(user.getId()).get().getFavoriteActorsIDs().contains(actorID))
                    .map(this.actorService::getActorByID)
                    .toList();
            if (actorsFromPreferences.isEmpty()) {
                return;
            }
            this.mailSender.send(user.getEmail(), "New Film with beloved actors",
                    String.format("A new film \"%s\" was released starring %s",
                            film.getName(),
                            String.join(", ", actorsFromPreferences
                                    .stream()
                                    .map(Actor::getFullName)
                                    .toList())));
        });
    }

    private int compareFilms(Film firstFilm, Film secondFilm, List<Film> favoriteFilms, List<Long> favoriteActorsIDs) {
        var firstFilmScore = this.getSearchScoreForFilm(firstFilm, favoriteFilms, favoriteActorsIDs);
        var secondFilmScore = this.getSearchScoreForFilm(secondFilm, favoriteFilms, favoriteActorsIDs);
        return Double.compare(secondFilmScore, firstFilmScore);
    }

    /**
     * get a score of specific film for filtration purposes.
     * The sort logic is next:
     *      MAX_VALUE if film presented in favorites,
     *      + 100 if one of actors presented in favorites
     *      + (filmGenrePreferencePercent) * 10
     * @param film film to get score for
     * @param favoriteFilms list of favorite films
     * @param favoriteActorsIDs list of favorite actors ids
     * @return score for specific film
     */

    private double getSearchScoreForFilm(Film film, List<Film> favoriteFilms, List<Long> favoriteActorsIDs) {
        var currentScore = 0d;
        var filmInPreferences = favoriteFilms
                .stream()
                .anyMatch(favoriteFilm -> favoriteFilm.getId() == film.getId());

        if (filmInPreferences) { return Integer.MAX_VALUE; }

        if (film.getActorsID() != null) {
            for (var actorID : film.getActorsID()) {
                if (currentScore > Integer.MAX_VALUE - 100) { break; }
                else if (favoriteActorsIDs.contains(actorID)) { currentScore += 100; }
            }
        }

        var favoriteFilmsWithSameGenreCount = favoriteFilms
                .stream()
                .filter(favoriteFilm -> favoriteFilm.getGenre() == film.getGenre())
                .toList()
                .size();
        if (favoriteFilmsWithSameGenreCount > 0) {
            var addScoreForGenre = 100 / ((double)favoriteFilms.size() / favoriteFilmsWithSameGenreCount) * 10;
            if (currentScore <= Integer.MAX_VALUE - addScoreForGenre) { currentScore += addScoreForGenre; }
        }

        return currentScore;
    }
}
