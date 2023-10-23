package com.example.spring2023.Domain.models;

import lombok.Getter;
import org.springframework.lang.Nullable;

import java.util.List;

@Getter
public class Film {
    @Nullable
    private Long id;
    private String name;
    private int releaseYear;
    @Nullable
    private List<Long> actorsID;

    public void setActorsID(@Nullable List<Long> actorsID) {
        this.actorsID = actorsID;
    }

    public Film(@Nullable Long id, String name, int releaseYear, @Nullable List<Long> actorsID) {
        this.id = id;
        this.name = name;
        this.releaseYear = releaseYear;
        this.actorsID = actorsID;
    }
}
