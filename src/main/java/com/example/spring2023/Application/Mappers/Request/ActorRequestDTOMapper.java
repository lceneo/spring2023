package com.example.spring2023.Application.Mappers.Request;

import com.example.spring2023.Domain.DTO.RequestDTO.ActorRequestDTO;
import com.example.spring2023.Domain.mappers.Request.IActorRequestDTOMapper;
import com.example.spring2023.Domain.mappers.Request.IFilmRequestDTOMapper;
import com.example.spring2023.Domain.models.Actor;
import org.springframework.stereotype.Service;

@Service
public class ActorRequestDTOMapper implements IActorRequestDTOMapper {
    @Override
    public Actor apply(ActorRequestDTO actorRequestDTO) {
        return new Actor(
                actorRequestDTO.getId(),
                actorRequestDTO.getName(),
                actorRequestDTO.getSurname(),
                actorRequestDTO.getPatronic(),
                actorRequestDTO.getAge()
        );
    }
}
