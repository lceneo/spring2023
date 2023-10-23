package com.example.spring2023.Domain.mappers.Response;

import com.example.spring2023.Domain.DTO.ResponseDTO.FilmResponseDTO;
import com.example.spring2023.Domain.models.Actor;
import com.example.spring2023.Domain.models.Film;
import org.springframework.lang.Nullable;

import java.util.List;

public interface IFilmResponseDTOMapper {
    FilmResponseDTO apply(@Nullable Film film, @Nullable List<Actor> actors);

}
