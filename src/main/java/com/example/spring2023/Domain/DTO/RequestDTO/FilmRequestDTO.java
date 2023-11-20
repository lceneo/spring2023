package com.example.spring2023.Domain.DTO.RequestDTO;

import lombok.Getter;
import org.springframework.lang.Nullable;

import java.util.List;

public class FilmRequestDTO {
    @Nullable
    private Long id;
    @Getter
    private String name;
    
    @Getter
    private String genre;

    @Getter
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
}
