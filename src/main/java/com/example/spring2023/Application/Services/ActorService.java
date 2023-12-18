package com.example.spring2023.Application.Services;

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

    private final IUserPreferencesService userPreferencesService;
    @Autowired
    public ActorService(
            IActorRepository actorRepository,
            IActorRequestDTOMapper actorRequestDTOMapper,
            IUserPreferencesService userPreferencesService) {
        this.actorRepository = actorRepository;
        this.actorRequestDTOMapper = actorRequestDTOMapper;
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
            var favoriteActorsIDs = this.userPreferencesService.getMyPreferences(user.get()).getFavoriteActorsIDs();
            actors.sort((firstActor, secondActor) -> {
                var firstActorInPreferences = favoriteActorsIDs.contains(firstActor.getId());
                var secondActorInPreferences = favoriteActorsIDs.contains(secondActor.getId());

                if (firstActorInPreferences == secondActorInPreferences) { return 0; }
                else if (secondActorInPreferences) { return 1; }
                else { return -1; }
            });
        }

        return actors;
    }

    public Actor getActorByID(Long id) {
        return this.actorRepository.findById(id).orElse(null);
    }

    public void deleteActor(Long id) {
        this.actorRepository.deleteById(id);
    }
}
