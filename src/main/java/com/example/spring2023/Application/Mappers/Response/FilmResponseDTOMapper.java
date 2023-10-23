package com.example.spring2023.Application.Mappers.Response;

import com.example.spring2023.Domain.DTO.ResponseDTO.FilmResponseDTO;
import com.example.spring2023.Domain.mappers.Response.IFilmResponseDTOMapper;
import com.example.spring2023.Domain.models.Actor;
import com.example.spring2023.Domain.models.Film;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmResponseDTOMapper implements IFilmResponseDTOMapper {

    public FilmResponseDTO apply(@Nullable Film film, @Nullable List<Actor> actors) {
        if (film == null) { return  null; }
        return new FilmResponseDTO(
                film.getId(),
                film.getName(),
                film.getReleaseYear(),
                film.getActorsID() != null ? film.getActorsID()
                        .stream()
                        .map(id -> this.findActor(id, actors))
                        .filter(actor -> actor != null)
                        .toList() : null
        );
    }

    private Actor findActor(Long actorID, List<Actor> actors) {
        for (var actor: actors) {
            if (actor.getId() == actorID) { return actor; }
        }
        return null;
    }
}
