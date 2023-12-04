package com.example.spring2023.DAL.repositories;

import com.example.spring2023.Domain.models.User;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends CrudRepository<UserDetails, Long> {

    @Query("SELECT * FROM _user u WHERE u.login = :login")
    Optional<User> findByLogin(String login);

    @Query("INSERT INTO _user (login, password, email, firstname, secondname, patronic)" +
            " VALUES (:#{#user.login}, :#{#user.password}, :#{#user.email}, :#{#user.firstName}, :#{#user.secondName}, :#{#user.patronic})" +
            "RETURNING *")
    User createUser(User user);

    @Query("SELECT * FROM _user WHERE id = :id")
    Optional<User> findByID(Long id);

    @Query("SELECT * FROM _user")
    List<User> getAll();

    @Query("INSERT INTO user_favorite_films (userID, filmID) VALUES (:userID, :filmID) ON CONFLICT DO NOTHING ")
    @Modifying
    void addFilmToFavorites(Long userID, Long filmID);

    @Query("INSERT INTO user_favorite_actors (userID, actorid) VALUES (:userID, :actorID) ON CONFLICT DO NOTHING ")
    @Modifying
    void addActorToFavorites(Long userID, Long actorID);

    @Query("SELECT actorid FROM user_favorite_actors WHERE userid = :userID")
    List<Long> findFavoriteActors(Long userID);

    @Query("SELECT filmid FROM user_favorite_films WHERE userid = :userID")
    List<Long> findFavoriteFilms(Long userID);

}
