package com.example.spring2023.Application.Services;

import com.example.spring2023.Domain.DTO.RequestDTO.FilmRequestDTO;
import com.example.spring2023.Domain.models.Actor;
import com.example.spring2023.Domain.models.Film;
import com.example.spring2023.DAL.repositories.IActorRepository;
import com.example.spring2023.DAL.repositories.IFilmActorRepository;
import com.example.spring2023.DAL.repositories.IFilmRepository;
import com.example.spring2023.Domain.services.IFilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;

@Transactional
@Service
public class FilmService implements IFilmService {

    private final IFilmRepository filmRepository;
    private final IActorRepository actorRepository;
    private final IFilmActorRepository filmActorRepository;

    @Autowired
    public FilmService(
            IFilmRepository filmRepository,
            IActorRepository actorRepository,
            IFilmActorRepository filmActorRepository) {
        this.filmRepository = filmRepository;
        this.actorRepository = actorRepository;
        this.filmActorRepository = filmActorRepository;
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
        }
        return new SimpleEntry<>(updatedOrCreatedFilm, this.actorRepository.findAll());
    }
    public SimpleEntry<List<Film>, List<Actor>> getFilms(@Nullable String name, @Nullable String genre, @Nullable Integer releaseYear) {
        var films = this.filmRepository.findAll()
                .stream()
                .filter(film ->
                        film.getName().toLowerCase().contains(name != null ? name.toLowerCase() : "") &&
                        (genre == null || film.getGenre().equalsIgnoreCase(genre)) &&
                        (releaseYear == null || film.getReleaseYear() == releaseYear))
                .toList();

        films.forEach(film -> {
             var filmActors = this.filmActorRepository.getAllFilmActors(film.getId());
             film.setActorsID(filmActors);
        });
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
}
