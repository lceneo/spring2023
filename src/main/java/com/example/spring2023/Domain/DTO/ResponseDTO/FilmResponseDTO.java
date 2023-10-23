package com.example.spring2023.Domain.DTO.ResponseDTO;

import com.example.spring2023.Domain.models.Actor;
import lombok.Getter;
import org.springframework.lang.Nullable;

import java.util.List;

public class FilmResponseDTO {

    private Long id;
    @Getter
    private String name;
    @Getter
    private int releaseYear;
    @Nullable
    private List<Actor> actors;

    @Nullable
    public Long getId() {
        return id;
    }

    @Nullable
    public List<Actor> getActors() {
        return actors;
    }

    public FilmResponseDTO(Long id, String name, int releaseYear, @Nullable List<Actor> actors) {
        this.id = id;
        this.name = name;
        this.releaseYear = releaseYear;
        this.actors = actors;
    }
}
