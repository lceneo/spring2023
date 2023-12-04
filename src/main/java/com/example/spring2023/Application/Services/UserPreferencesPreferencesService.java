package com.example.spring2023.Application.Services;

import com.example.spring2023.DAL.repositories.IUserRepository;
import com.example.spring2023.Domain.models.UserPreferences;
import com.example.spring2023.Domain.services.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserPreferencesPreferencesService implements IUserPreferencesService {

    private final IUserService userService;
    private final IUserRepository userRepository;
    private final IActorService actorService;

    public UserPreferencesPreferencesService(IUserService userService,
                                             IUserRepository userRepository,
                                             IActorService actorService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.actorService = actorService;
    }

    public UserPreferences getMyPreferences(String token) {
        var userID = this.userService.getUser(token).get().getId();
        var films = this.userRepository.findFavoriteFilms(userID);
        var actors = this.userRepository.findFavoriteActors(userID);
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

    public void addFilmToPreferences(Long filmID, String token) {
        var user = this.userService.getUser(token);
        this.userRepository.addFilmToFavorites(user.get().getId(), filmID);
    }

    public void addActorToPreferences(Long actorID, String token) {
        var user = this.userService.getUser(token);
        this.userRepository.addActorToFavorites(user.get().getId(), actorID);
    }
}
