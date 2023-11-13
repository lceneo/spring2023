package com.example.spring2023.Domain.services;

import com.example.spring2023.Domain.models.UserPreferences;

import java.util.Optional;

public interface IUserService {
    /**
     *
     * @param token userToken passed with a request
     * @return preferences of current user
     */
    UserPreferences getMyPreferences(String token);

    /**
     *
     * @param userID id of user whose preferences to return
     * @return user preferences
     */
    Optional<UserPreferences> getUserPreferences(Long userID);

    /**
     *
     * @param filmID - ID of film to add to favorites
     */
    void addFilmToPreferences(Long filmID, String token);

    /**
     *
     * @param filmID - ID of actor to add to favorites
     */
    void addActorToPreferences(Long filmID, String token);
}
