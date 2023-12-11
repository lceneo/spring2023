package com.example.spring2023.Application.Services;

import com.example.spring2023.DAL.repositories.IUserRepository;
import com.example.spring2023.Domain.models.User;
import com.example.spring2023.Domain.models.UserPreferences;
import com.example.spring2023.Domain.services.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserPreferencesPreferencesService implements IUserPreferencesService {

    private final IUserRepository userRepository;

    public UserPreferencesPreferencesService(
                                             IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserPreferences getMyPreferences(User user) {
        var films = this.userRepository.findFavoriteFilms(user.getId());
        var actors = this.userRepository.findFavoriteActors(user.getId());
        return new UserPreferences(films, actors);
    }

    public Optional<UserPreferences> getUserPreferences(Long userID) {
        var user = this.userRepository.findByID(userID);
        if (user.isPresent()) {
            var films = this.userRepository.findFavoriteFilms(userID);
            var actors = this.userRepository.findFavoriteActors(userID);
            return Optional.of(new UserPreferences(films, actors));
        }
        return Optional.empty();
    }

    public void addFilmToPreferences(Long filmID, User user) {
        this.userRepository.addFilmToFavorites(user.getId(), filmID);
    }

    public void addActorToPreferences(Long actorID, User user) {
        this.userRepository.addActorToFavorites(user.getId(), actorID);
    }

    public void removeFilmFromPreferences(Long filmID, User user) {
        this.userRepository.removeFilmFromFavorites(user.getId(), filmID);
    }

    public void removeActorFromPreferences(Long actorID, User user) {
        this.userRepository.removeActorFromFavorites(user.getId(), actorID);
    }
}
