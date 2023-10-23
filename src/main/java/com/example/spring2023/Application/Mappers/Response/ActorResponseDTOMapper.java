package com.example.spring2023.Application.Mappers.Response;
import com.example.spring2023.Domain.DTO.ResponseDTO.ActorResponseDTO;
import com.example.spring2023.Domain.mappers.Response.IActorResponseDTOMapper;
import com.example.spring2023.Domain.models.Actor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public class ActorResponseDTOMapper implements IActorResponseDTOMapper {
    public ActorResponseDTO apply(@Nullable Actor actor) {
        if (actor == null) {
            return null;
        }
        return new ActorResponseDTO(
                actor.getId(),
                actor.getName(),
                actor.getSurname(),
                actor.getPatronic(),
                actor.getAge()
        );
    }

}
