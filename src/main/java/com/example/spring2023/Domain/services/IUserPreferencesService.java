package com.example.spring2023.Domain.services;

import com.example.spring2023.Domain.models.User;
import com.example.spring2023.Domain.models.UserPreferences;

import java.util.Optional;

public interface IUserPreferencesService {
    /**
     *
     * @param user who made a request
     * @return preferences of current user
     */
    UserPreferences getMyPreferences(User user);

    /**
     *
     * @param userID id of user whose preferences to return
     * @return user preferences
     */
    Optional<UserPreferences> getUserPreferences(Long userID);

    /**
     *
     * @param filmID - ID of film to add to favorites
     * @param user - user who made a request
     */
    void addFilmToPreferences(Long filmID, User user);

    /**
     *
     * @param actorID - ID of actor to add to favorites
     * @param user - user who made a request
     */
    void addActorToPreferences(Long actorID, User user);

    /**
     *
     * @param filmID - ID of film to remove from preferences
     * @param user - user who wants to remove film from preferences
     */
    void removeFilmFromPreferences(Long filmID, User user);

    /**
     *
     * @param actorID - ID of actor to remove from preferences
     * @param user - user who wants to remove actor from preferences
     */
    void removeActorFromPreferences(Long actorID, User user);
}
