package com.example.spring2023.DAL.repositories;
import com.example.spring2023.Domain.models.Actor;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

 public interface IActorRepository extends CrudRepository<Actor, Long> {

        @Override
        @Query("SELECT * FROM actors")
        List<Actor> findAll();

        @Override
        @Query("SELECT * FROM actors f" +
                " WHERE f.id = :id")
        Optional<Actor> findById(Long id);

        @Query("SELECT * FROM actors a " +
                "WHERE LOWER(CONCAT(a.name, a.surname, a.patronic))" +
                " LIKE LOWER(CONCAT('%',:searchStr, '%'))")
        List<Actor> findBySubstring(String searchStr);

        @Override
        @Query("INSERT INTO actors (name, age, patronic, surname)" +
                " VALUES (:#{#actor.name}, :#{#actor.age}, :#{#actor.patronic}, :#{#actor.surname})" +
                " RETURNING id, name, surname, patronic, age")
        Actor save(Actor actor);

        @Query("UPDATE actors" +
                " SET name = :#{#actor.name}, surname = :#{#actor.surname}, patronic = :#{#actor.patronic}, age = :#{#actor.age}" +
                " WHERE id = :#{#actor.id}" +
                " RETURNING id, name, surname, patronic, age")
        Actor update(Actor actor);

        @Override
        @Modifying
        @Query("DELETE FROM actors" +
                " WHERE id = :id")
        void deleteById(Long id);
    }
