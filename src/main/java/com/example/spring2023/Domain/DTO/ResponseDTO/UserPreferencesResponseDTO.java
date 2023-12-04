package com.example.spring2023.Domain.DTO.ResponseDTO;

import com.example.spring2023.Domain.models.Actor;
import com.example.spring2023.Domain.models.Film;
import lombok.Getter;

import java.util.List;

@Getter
public class UserPreferencesResponseDTO {
    private List<Film> favoriteFilms;
    private List<Actor> favoriteActors;

    public void setFavoriteFilms(List<Film> favoriteFilms) {
        this.favoriteFilms = favoriteFilms;
    }

    public void setFavoriteActors(List<Actor> favoriteActors) {
        this.favoriteActors = favoriteActors;
    }

    public UserPreferencesResponseDTO(List<Film> favoriteFilms, List<Actor> favoriteActors) {
        this.favoriteFilms = favoriteFilms;
        this.favoriteActors = favoriteActors;
    }
}
