package com.example.spring2023.Domain.mappers.Request;

import com.example.spring2023.Domain.DTO.RequestDTO.ActorRequestDTO;
import com.example.spring2023.Domain.models.Actor;

public interface IActorRequestDTOMapper {
    Actor apply(ActorRequestDTO actorRequestDTO);
}
