package com.example.spring2023.Domain.mappers.Request;
import com.example.spring2023.Domain.DTO.RequestDTO.FilmRequestDTO;
import com.example.spring2023.Domain.models.Film;

public interface IFilmRequestDTOMapper {
    Film apply(FilmRequestDTO filmRequestDTO);
}
