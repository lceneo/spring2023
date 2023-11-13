package com.example.spring2023.Domain.models;

import lombok.Getter;

import java.util.List;

@Getter
public class UserPreferences {
    private List<Film> favoriteFilms;
    private List<Actor> favoriteActors;

    public void setFavoriteFilms(List<Film> favoriteFilms) {
        this.favoriteFilms = favoriteFilms;
    }

    public void setFavoriteActors(List<Actor> favoriteActors) {
        this.favoriteActors = favoriteActors;
    }

    public UserPreferences(List<Film> favoriteFilms, List<Actor> favoriteActors) {
        this.favoriteFilms = favoriteFilms;
        this.favoriteActors = favoriteActors;
    }
}
