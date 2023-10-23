package com.example.spring2023.DAL.repositories;
import com.example.spring2023.Domain.models.Film;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface IFilmRepository extends CrudRepository<Film, Long> {

    @Override
    @Query("SELECT * FROM films")
    List<Film> findAll();

    @Override
    @Query("SELECT * FROM films f WHERE f.id = :id")
    Optional<Film> findById(Long id);

    @Query("SELECT * FROM films f WHERE LOWER(f.name) LIKE LOWER(CONCAT('%',:name, '%'))")
    List<Film> findBySubstring(String name);

    @Query("INSERT INTO films (name, release_year) VALUES (:name, :releaseYear) RETURNING id, name, release_year")
    Film saveFilm(String name, int releaseYear);

    @Query("UPDATE films SET name = :name, release_year = :releaseYear WHERE id = :id RETURNING id, name, release_year")
    Film update(Long id, String name, int releaseYear);

    @Override
    @Modifying
    @Query("DELETE FROM films WHERE id = :id")
    void deleteById(Long id);
}
