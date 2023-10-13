package com.example.spring2023.Application.Services;

import com.example.spring2023.DAL.PostgreDb;
import com.example.spring2023.Domain.models.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmService {

    private final PostgreDb db;
    @Autowired
    public FilmService(
            PostgreDb db) {
        this.db = db;
    }

    public Film insertFilm(Film film) throws Exception {
        return this.db.insertFilm(film);
    }
    public List<Film> getFilms() {
        return this.db.getFilms();
    }
}
