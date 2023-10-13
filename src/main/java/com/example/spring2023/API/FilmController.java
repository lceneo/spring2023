package com.example.spring2023.API;

import com.example.spring2023.Application.Services.FilmService;
import com.example.spring2023.Domain.models.Film;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FilmController {

    private final FilmService filmService;
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/films")
    public List<Film> films() {
        return this.filmService.getFilms();
    }

    @PostMapping("/films")
    public Film films(
            @RequestBody Film film
    ) throws Exception {
        return this.filmService.insertFilm(film);
    }
}
