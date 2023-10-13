package com.example.spring2023.DAL;

import com.example.spring2023.Domain.models.Actor;
import com.example.spring2023.Domain.models.Film;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostgreDb {
    private final JdbcTemplate jdbc;

    public PostgreDb(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public Actor insertActor(Actor actor) throws Exception {
       var query = "INSERT INTO actors VALUES (NULL, ?, ?, ?, ?)";
       try {
           jdbc.update(query,
                   actor.getName(),
                   actor.getSurname(),
                   actor.getPatronic(),
                   actor.getAge());
           return actor;
       }
        catch (Exception err) {
           throw new Exception("Unable to insert user");
        }
    }

    public List<Actor> getActors() {
        var query = "SELECT * FROM actors";
        RowMapper<Actor> actorRowMapper = (r, i) -> {
            var rowObj = new Actor();
            rowObj.setId(r.getInt("id"));
            rowObj.setName(r.getString("name"));
            rowObj.setSurname(r.getString("surname"));
            rowObj.setPatronic(r.getString("patronic"));
            rowObj.setAge(r.getInt("age"));
            return rowObj;
        };
        return jdbc.query(query, actorRowMapper);
    }

    public List<Film> getFilms() {
        var query = "SELECT * FROM films";
        RowMapper<Film> filmRowMapper = (r, i) -> {
            var rowObj = new Film();
            rowObj.setId(r.getInt("id"));
            rowObj.setName(r.getString("name"));
            rowObj.setReleaseYear(r.getInt("release_year"));
            return rowObj;
        };
        return jdbc.query(query, filmRowMapper);
    }

    public Film insertFilm(Film film) throws Exception {
        var query = "INSERT INTO films (name, release_year) VALUES (?, ?)";
        try {
            jdbc.update(query,
                    film.getName(),
                    film.getReleaseYear());
            return film;
        }
        catch (Exception err) {
            throw new Exception("Unable to insert film");
        }
    }
}
