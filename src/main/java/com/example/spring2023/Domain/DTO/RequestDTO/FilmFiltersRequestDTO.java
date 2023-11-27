package com.example.spring2023.Domain.DTO.RequestDTO;

import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
public class FilmFiltersRequestDTO {
    /**
     * substring to filter films with
     */
    @Nullable String searchStr;
    /**
     * genre to filter films with
     */
    @Nullable String genre;
    /**
     * release year to filter films with
     */
    @Nullable Integer releaseYear;

    public void setSearchStr(@Nullable String searchStr) {
        this.searchStr = searchStr;
    }

    public void setGenre(@Nullable String genre) {
        this.genre = genre;
    }

    public void setReleaseYear(@Nullable Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public FilmFiltersRequestDTO(@Nullable String searchStr, @Nullable String genre, @Nullable Integer releaseYear) {
        this.searchStr = searchStr;
        this.genre = genre;
        this.releaseYear = releaseYear;
    }
}
