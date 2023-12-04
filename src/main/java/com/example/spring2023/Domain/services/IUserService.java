package com.example.spring2023.Domain.services;

import com.example.spring2023.Domain.models.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    /**
     *
     * @param token - token to find user with
     * @return found user
     */
    Optional<User> getUser(String token);

    /**
     *
     * @return all created users
     */
    List<User> getUsers();
}
