package com.example.spring2023.API.controllers;

import com.example.spring2023.Domain.DTO.RequestDTO.FilmRequestDTO;
import com.example.spring2023.Domain.DTO.ResponseDTO.FilmResponseDTO;
import com.example.spring2023.Application.Mappers.Response.FilmResponseDTOMapper;
import com.example.spring2023.Domain.mappers.Response.IFilmResponseDTOMapper;
import com.example.spring2023.Domain.services.IFilmService;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {

    private final IFilmService filmService;
    private final IFilmResponseDTOMapper filmResponseDTOMapper;
    public FilmController(IFilmService filmService,
                          IFilmResponseDTOMapper filmResponseDTOMapper) {
        this.filmService = filmService;
        this.filmResponseDTOMapper = filmResponseDTOMapper;
    }

    @GetMapping
    public List<FilmResponseDTO> films(
            @RequestParam @Nullable String name
    ) {
        var films = this.filmService.getFilms(name);
        return films.getKey().stream()
                .map(film -> this.filmResponseDTOMapper.apply(film, films.getValue()))
                .toList();
    }

    @GetMapping("/{id}")
    public FilmResponseDTO filmByID(
            @PathVariable Long id) {
        var film = this.filmService.getFilmByID(id);
        if (film == null) { return null; }
        return this.filmResponseDTOMapper.apply(film.getKey(), film.getValue());
    }
    @PostMapping
    public FilmResponseDTO films(
            @RequestBody FilmRequestDTO film
    ){
        var filmTuple = this.filmService.createOrUpdateFilm(film);
        return this.filmResponseDTOMapper.apply(filmTuple.getKey(), filmTuple.getValue());
    }

    @DeleteMapping("/{id}")
    public void film(
            @PathVariable Long id) {
        this.filmService.deleteFilm(id);
    }

}
