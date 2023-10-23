package com.example.spring2023.Domain.services;
import com.example.spring2023.Domain.DTO.RequestDTO.ActorRequestDTO;
import com.example.spring2023.Domain.models.Actor;
import org.springframework.lang.Nullable;

import java.util.List;

public interface IActorService {

    /**
     * Create or update Actor
     * @param actor - existing or new Actor
     * @return created or updated Actor
     */
    Actor createOrUpdateActor(ActorRequestDTO actor);

    /**
     * Get All actors satisfying search query from DB
     * @param name - substring to search actors with
     * @return the list of suitable actors
     */
    List<Actor> getActors(@Nullable String name);

    /**
     * Get Actor from DB by ID
     * @param id - ID of actor to return
     * @return Actor with specified ID or null if not found
     */
    Actor getActorByID(Long id);

    /**
     * Delete actor from DB
     * @param id - Actor ID to delete
     */
    void deleteActor(Long id);

}
