package com.example.spring2023.Domain.DTO.RequestDTO;

import jakarta.validation.constraints.*;
import lombok.Getter;
import org.springframework.lang.Nullable;

import java.time.Year;
import java.util.Date;
import java.util.List;

public class FilmRequestDTO {

    @Nullable
    private Long id;

    @Getter
    @NotEmpty(message = "Name is required")
    @Size(min = 3, max = 30, message = "Name must contain from 3 to 30 characters")
    private String name;

    @Getter
    @NotEmpty(message = "Genre is required")
    @Size(min = 3, max = 15, message = "Genre must contain from 3 to 15 characters")
    private String genre;

    @Getter
    @NotNull(message = "releaseYear is required")
    @Min(value = 1800, message = "minimum releaseYear value is 1800")
    private int releaseYear;
    @Nullable
    private List<Long> actorsID;

    @Nullable
    public Long getId() {
        return id;
    }

    @Nullable
    public List<Long> getActorsID() {
        return actorsID;
    }

    public FilmRequestDTO(@Nullable Long id, String name, String genre, int releaseYear, @Nullable List<Long> actorsID) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.actorsID = actorsID;
    }
}
