package com.example.spring2023.Domain.models;

import lombok.Getter;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Objects;

@Getter
public class Film {
    /**
     * id of a Film
     */
    @Nullable
    private Long id;
    /**
     * film name
     */
    private String name;
    /**
     * genre of a film
     */
    private String genre;
    /**
     * release year of a film
     */
    private int releaseYear;
    /**
     * list of actors who did star in a particular film
     */
    @Nullable
    private List<Long> actorsID;

    public void setActorsID(@Nullable List<Long> actorsID) {
        this.actorsID = actorsID;
    }

    public Film(@Nullable Long id, String name, String genre, int releaseYear, @Nullable List<Long> actorsID) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.actorsID = actorsID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return releaseYear == film.releaseYear
                && Objects.equals(id, film.id)
                && Objects.equals(name, film.name)
                && Objects.equals(genre, film.genre)
                && Objects.equals(actorsID, film.actorsID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, genre, releaseYear, actorsID);
    }
}
