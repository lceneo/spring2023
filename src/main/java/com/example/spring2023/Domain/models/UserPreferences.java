package com.example.spring2023.Domain.models;

import lombok.Getter;

import java.util.List;

@Getter
public class UserPreferences {
    private List<Long> favoriteFilmsIDs;
    private List<Long> favoriteActorsIDs;

    public void setFavoriteFilms(List<Long> favoriteFilmsIDs) {
        this.favoriteFilmsIDs = favoriteFilmsIDs;
    }

    public void setFavoriteActors(List<Long> favoriteActorsIDs) {
        this.favoriteActorsIDs = favoriteActorsIDs;
    }

    public UserPreferences(List<Long> favoriteFilms, List<Long> favoriteActors) {
        this.favoriteFilmsIDs = favoriteFilms;
        this.favoriteActorsIDs = favoriteActors;
    }
}
