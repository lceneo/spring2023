package com.example.spring2023.Application.Services;

import com.example.spring2023.DAL.repositories.IUserRepository;
import com.example.spring2023.Domain.models.UserPreferences;
import com.example.spring2023.Domain.services.IActorService;
import com.example.spring2023.Domain.services.IFilmService;
import com.example.spring2023.Domain.services.IJWTService;
import com.example.spring2023.Domain.services.IUserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final IFilmService filmService;
    private final IActorService actorService;
    private final IJWTService jwtService;

    public UserService(IUserRepository userRepository,
                       IFilmService filmService,
                       IActorService actorService,
                       IJWTService jwtService) {
        this.userRepository = userRepository;
        this.filmService = filmService;
        this.actorService = actorService;
        this.jwtService = jwtService;
    }

    public UserPreferences getMyPreferences(String token) {
        var login = jwtService.extractUserLogin(token);
        var userID = this.userRepository.findByLogin(login).get().getId();
        var films = this.userRepository.findFavoriteFilms(userID);
        var actors = this.userRepository.findFavoriteActors(userID);
        return new UserPreferences(
                films.stream().map(id -> this.filmService.getFilmByID(id).getKey()).toList(),
                actors.stream().map(this.actorService::getActorByID).toList()
        );
    }

    public Optional<UserPreferences> getUserPreferences(Long userID) {
        var user = this.userRepository.findByID(userID);
        if (user.isPresent()) {
            var films = this.userRepository.findFavoriteFilms(userID);
            var actors = this.userRepository.findFavoriteActors(userID);
            return Optional.of(new UserPreferences(
                    films.stream().map(id -> this.filmService.getFilmByID(id).getKey()).toList(),
                    actors.stream().map(this.actorService::getActorByID).toList()
            ));
        }
        return Optional.empty();
    }

    public void addFilmToPreferences(Long filmID, String token) {
        var login = this.jwtService.extractUserLogin(token);
        var user = this.userRepository.findByLogin(login);
        this.userRepository.addFilmToFavorites(user.get().getId(), filmID);
    }

    public void addActorToPreferences(Long actorID, String token) {
        var login = this.jwtService.extractUserLogin(token);
        var user = this.userRepository.findByLogin(login);
        this.userRepository.addActorToFavorites(user.get().getId(), actorID);
    }
}
