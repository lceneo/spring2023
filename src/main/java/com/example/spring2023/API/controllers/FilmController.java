package com.example.spring2023.API.controllers;

import com.example.spring2023.Application.utils.ErrorMessage;
import com.example.spring2023.Domain.DTO.RequestDTO.FilmFiltersRequestDTO;
import com.example.spring2023.Domain.DTO.RequestDTO.FilmRequestDTO;
import com.example.spring2023.Domain.DTO.ResponseDTO.FilmResponseDTO;
import com.example.spring2023.Domain.mappers.Response.IFilmResponseDTOMapper;
import com.example.spring2023.Domain.models.User;
import com.example.spring2023.Domain.services.IFilmService;
import com.example.spring2023.Domain.services.IUserService;
import jakarta.validation.Valid;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/films")
public class FilmController {

    private final IFilmService filmService;
    private final IFilmResponseDTOMapper filmResponseDTOMapper;
    private final IUserService userService;

    private final ErrorMessage errorMessage;
    public FilmController(IFilmService filmService,
                          IFilmResponseDTOMapper filmResponseDTOMapper,
                          IUserService userService, ErrorMessage errorMessage) {
        this.filmService = filmService;
        this.filmResponseDTOMapper = filmResponseDTOMapper;
        this.userService = userService;
        this.errorMessage = errorMessage;
    }

    @GetMapping
    public List<FilmResponseDTO> films(
            @Nullable FilmFiltersRequestDTO filters,
            @RequestHeader(value = "Authorization", required = false) @Nullable String token
    ) {
        Optional<User> user = token != null ? this.userService.getUser(token.substring(7)) : Optional.empty();

        var films = this.filmService.getFilms(filters, user);
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
    public ResponseEntity films(
            @RequestBody @Valid FilmRequestDTO film,
            BindingResult bindingResult,
            @RequestHeader(value = "Authorization", required = false) @Nullable String token
    ){
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(this.errorMessage.createErrorMessage(bindingResult));
        }
        var filmTuple = this.filmService.createOrUpdateFilm(film);
        return ResponseEntity.ok(this.filmResponseDTOMapper.apply(filmTuple.getKey(), filmTuple.getValue()));
    }

    @DeleteMapping("/{id}")
    public void film(
            @PathVariable Long id) {
        this.filmService.deleteFilm(id);
    }

}
