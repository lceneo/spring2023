package com.example.spring2023.Application.Mappers.Request;

import com.example.spring2023.Domain.DTO.RequestDTO.FilmRequestDTO;
import com.example.spring2023.Domain.mappers.Request.IFilmRequestDTOMapper;
import com.example.spring2023.Domain.models.Film;
import org.springframework.stereotype.Service;


@Service
public class FilmRequestDTOMapper implements IFilmRequestDTOMapper {
    @Override
    public Film apply(FilmRequestDTO filmRequestDTO) {
        return new Film(
                filmRequestDTO.getId(),
                filmRequestDTO.getName(),
                filmRequestDTO.getGenre(),
                filmRequestDTO.getReleaseYear(),
                filmRequestDTO.getActorsID()
        );
    }
}
