package com.example.spring2023.Domain.services;
import com.example.spring2023.Domain.DTO.RequestDTO.FilmFiltersRequestDTO;
import com.example.spring2023.Domain.DTO.RequestDTO.FilmRequestDTO;
import com.example.spring2023.Domain.models.Actor;
import com.example.spring2023.Domain.models.Film;
import com.example.spring2023.Domain.models.User;
import org.springframework.lang.Nullable;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Optional;

public interface IFilmService {
    /**
     * Creates or updates existing Film
     * @param film - Film to update/create
     * @return created or updated Film
     */
    SimpleEntry<Film, List<Actor>> createOrUpdateFilm(FilmRequestDTO film);

    /**
     * get all films from DB that satisfy given search query
     * @param filters - subset of filters to use on actors list
     * @param user - user to take sort criteria from
     * @return - list of films that satisfy given criteria
     */
    SimpleEntry<List<Film>, List<Actor>> getFilms(FilmFiltersRequestDTO filters, Optional<User> user);

    /**
     * Get Film by ID
     * @param id - ID of desired film
     * @return Film from DB
     */
    SimpleEntry<Film, List<Actor>> getFilmByID(Long id);

    /**
     * Delete existing Film
     * @param id - ID of Film that needs to be deleted
     */
    void deleteFilm(Long id);

}
