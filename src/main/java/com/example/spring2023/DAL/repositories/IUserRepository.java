package com.example.spring2023.DAL.repositories;

import com.example.spring2023.Domain.models.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface IUserRepository extends CrudRepository<UserDetails, Long> {

    @Query("SELECT * FROM _user u WHERE u.login = :login")
    Optional<User> findByLogin(String login);

    @Query("INSERT INTO _user (login, password, email, firstname, secondname, patronic)" +
            " VALUES (:#{#user.login}, :#{#user.password}, :#{#user.email}, :#{#user.firstName}, :#{#user.secondName}, :#{#user.patronic})" +
            "RETURNING *")
    User createUser(User user);
}
