package com.example.spring2023.Domain.models;

import lombok.Getter;

@Getter
public class Actor_Film {
    private Long ActorID;
    private Long FilmID;

    public void setActorID(Long actorID) {
        ActorID = actorID;
    }

    public void setFilmID(Long filmID) {
        FilmID = filmID;
    }
}
