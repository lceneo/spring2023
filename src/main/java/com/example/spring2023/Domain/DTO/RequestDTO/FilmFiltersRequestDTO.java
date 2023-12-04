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

    /**
     * number of films to skip from the resulted value
     */
    @Nullable Integer skip;

    /**
     * number of films to take from the resulted value
     */
    @Nullable Integer take;

    public void setSearchStr(@Nullable String searchStr) {
        this.searchStr = searchStr;
    }

    public void setGenre(@Nullable String genre) {
        this.genre = genre;
    }

    public void setReleaseYear(@Nullable Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setSkip(@Nullable Integer skip) {
        this.skip = skip;
    }

    public void setTake(@Nullable Integer take) {
        this.take = take;
    }

    public FilmFiltersRequestDTO(@Nullable String searchStr, @Nullable String genre, @Nullable Integer releaseYear,
                                 @Nullable Integer take, @Nullable Integer skip) {
        this.searchStr = searchStr;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.take = take;
        this.skip = skip;
    }
}
