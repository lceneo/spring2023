package com.example.spring2023.Domain.mappers.Response;

import com.example.spring2023.Domain.DTO.ResponseDTO.ActorResponseDTO;
import com.example.spring2023.Domain.models.Actor;
import org.springframework.lang.Nullable;

public interface IActorResponseDTOMapper {

    ActorResponseDTO apply(@Nullable Actor actor);
}
