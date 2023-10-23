package com.example.spring2023.Application.Services;

import com.example.spring2023.Domain.DTO.RequestDTO.ActorRequestDTO;
import com.example.spring2023.DAL.repositories.IActorRepository;
import com.example.spring2023.Domain.mappers.Request.IActorRequestDTOMapper;
import com.example.spring2023.Domain.models.Actor;
import com.example.spring2023.Domain.services.IActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActorService implements IActorService {
    private final IActorRepository actorRepository;
    private final IActorRequestDTOMapper actorRequestDTOMapper;
    @Autowired
    public ActorService(
            IActorRepository actorRepository,
            IActorRequestDTOMapper actorRequestDTOMapper) {
        this.actorRepository = actorRepository;
        this.actorRequestDTOMapper = actorRequestDTOMapper;
    }

    public Actor createOrUpdateActor(ActorRequestDTO actor) {
        var mappedActor = this.actorRequestDTOMapper.apply(actor);
        if (mappedActor.getId() != null)
            return this.actorRepository.update(mappedActor);
        return this.actorRepository.save(mappedActor);
    }
    public List<Actor> getActors(@Nullable String name) {
        var actors = new ArrayList<Actor>();
        var queryResult = name != null ? this.actorRepository.findBySubstring(name) : this.actorRepository.findAll();
        for (Actor actor: queryResult)
            actors.add(actor);
        return actors;

    }

    public Actor getActorByID(Long id) {
        return this.actorRepository.findById(id).orElse(null);
    }

    public void deleteActor(Long id) {
        this.actorRepository.deleteById(id);
    }
}
