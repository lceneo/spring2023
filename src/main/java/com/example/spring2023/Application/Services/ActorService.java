package com.example.spring2023.Application.Services;

import com.example.spring2023.DAL.repositories.IFilmActorRepository;
import com.example.spring2023.Domain.DTO.RequestDTO.ActorFiltersRequestDTO;
import com.example.spring2023.Domain.DTO.RequestDTO.ActorRequestDTO;
import com.example.spring2023.DAL.repositories.IActorRepository;
import com.example.spring2023.Domain.mappers.Request.IActorRequestDTOMapper;
import com.example.spring2023.Domain.models.Actor;
import com.example.spring2023.Domain.models.User;
import com.example.spring2023.Domain.services.IActorService;
import com.example.spring2023.Domain.services.IUserPreferencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActorService implements IActorService {
    private final IActorRepository actorRepository;
    private final IActorRequestDTOMapper actorRequestDTOMapper;
    private final IFilmActorRepository filmActorRepository;

    private final IUserPreferencesService userPreferencesService;
    @Autowired
    public ActorService(
            IActorRepository actorRepository,
            IActorRequestDTOMapper actorRequestDTOMapper,
            IFilmActorRepository filmActorRepository,
            IUserPreferencesService userPreferencesService) {
        this.actorRepository = actorRepository;
        this.actorRequestDTOMapper = actorRequestDTOMapper;
        this.filmActorRepository = filmActorRepository;
        this.userPreferencesService = userPreferencesService;
    }

    public Actor createOrUpdateActor(ActorRequestDTO actor) {
        var mappedActor = this.actorRequestDTOMapper.apply(actor);
        if (mappedActor.getId() != null)
            return this.actorRepository.update(mappedActor);
        return this.actorRepository.save(mappedActor);
    }
    public List<Actor> getActors(ActorFiltersRequestDTO filters, Optional<User> user) {
        var actors = this.actorRepository.findWithFilters(filters.getSearchStr(), filters.getAge(), filters.getSkip(), filters.getTake());

        if (user.isPresent()) {
            var preferences = this.userPreferencesService.getMyPreferences(user.get());
            var favoriteActorsIDs = preferences.getFavoriteActorsIDs();
            var favoriteFilmsIDs = preferences.getFavoriteFilmsIDs();
            actors.sort((firstActor, secondActor) -> this.compareActors(firstActor, secondActor, favoriteFilmsIDs, favoriteActorsIDs));
        }

        return actors;
    }

    private int compareActors(Actor firstActor, Actor secondActor, List<Long> favoriteFilmsIDs, List<Long> favoriteActorsIDs) {
        var firstFilmScore = this.getSearchScoreForActor(firstActor, favoriteFilmsIDs, favoriteActorsIDs);
        var secondFilmScore = this.getSearchScoreForActor(secondActor, favoriteFilmsIDs, favoriteActorsIDs);
        return Double.compare(secondFilmScore, firstFilmScore);
    }

    /**
     * get a score of specific actor for filtration purposes.
     * The sort logic is next:
     *      MAX_VALUE if actor presented in favorites,
     *      + 100 if actor starred in film from preferences
     * @param actor actor to get score for
     * @param favoriteFilmsIDs list of favorite films ids
     * @param favoriteActorsIDs list of favorite actors ids
     * @return score for specific actor
     */

    private double getSearchScoreForActor(Actor actor, List<Long> favoriteFilmsIDs, List<Long> favoriteActorsIDs) {
        var currentScore = 0d;
        var actorInPreferences = favoriteActorsIDs.contains(actor.getId());

        if (actorInPreferences) { return Integer.MAX_VALUE; }

        for (var filmID : favoriteFilmsIDs) {
                if (currentScore > Integer.MAX_VALUE - 100) { break; }
                var actorFilmsIDsInFavorites = this.filmActorRepository.getAllActorFilms(actor.getId())
                        .stream()
                        .filter(favoriteFilmsIDs::contains)
                        .toList();

                for (var favoriteFilmID: actorFilmsIDsInFavorites) {
                    if (currentScore > Integer.MAX_VALUE - 100) { break; }
                    currentScore += 100;
                }
        }
        return currentScore;
    }

    public Actor getActorByID(Long id) {
        return this.actorRepository.findById(id).orElse(null);
    }

    public void deleteActor(Long id) {
        this.actorRepository.deleteById(id);
    }
}
