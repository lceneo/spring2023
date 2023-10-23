package com.example.spring2023.DAL.repositories;
import com.example.spring2023.Domain.models.Actor;
import com.example.spring2023.Domain.models.Actor_Film;
import com.example.spring2023.Domain.models.Film;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IFilmActorRepository extends CrudRepository<Actor_Film, Actor_Film> {
    @Query("SELECT * FROM actor_film")
    List<Actor_Film> getAllActorsFilmsData();

    @Query("INSERT INTO actor_film (actorid, filmid) VALUES (:actorID, :filmID) On CONFLICT ON CONSTRAINT actor_film_pkey DO NOTHING")
    @Modifying
    void insertData(Long actorID, Long filmID);

    @Query("SELECT actorid FROM actor_film WHERE filmid = :filmID")
    List<Long> getAllFilmActors(Long filmID);

    @Query("SELECT filmid FROM actor_film WHERE actorid = :actorID")
    List<Long> getAllActorFilms(Long actorID);

    @Query("DELETE FROM actor_film WHERE actorid = :actorID")
    @Modifying
    void deleteActorFilms(Long actorID);

    @Query("DELETE FROM actor_film WHERE filmid = :filmID")
    @Modifying
    void deleteFilmActors(Long filmID);
}
