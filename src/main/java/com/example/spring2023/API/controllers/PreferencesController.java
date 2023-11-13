package com.example.spring2023.API.controllers;
import com.example.spring2023.Domain.models.UserPreferences;
import com.example.spring2023.Domain.services.IUserService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/profile")
public class PreferencesController {

    private final IUserService userService;

    public PreferencesController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/My")
    public UserPreferences getMyPreferences(
            @RequestHeader("Authorization") String token
    ) {
        return this.userService.getMyPreferences(token.substring(7));
    }

    @GetMapping("/{id}")
    public Optional<UserPreferences> getMyPreferences(
            @PathVariable Long id)
        {
        return this.userService.getUserPreferences(id);
    }

    @PostMapping("/film")
    public void addFilmToPreferences(
            Long filmID,
            @RequestHeader("Authorization") String token
    ) {
        this.userService.addFilmToPreferences(filmID, token.substring(7));
    }

    @DeleteMapping("/film")
    public void removeFilmToPreferences(
            Long filmID,
            @RequestHeader("Authorization") String token
    ) {

    }

    @PostMapping("/actor")
    public void addActorToPreferences(
            Long actorID,
            @RequestHeader("Authorization") String token
    ) {
        this.userService.addActorToPreferences(actorID, token.substring(7));
    }

    @DeleteMapping("/actor")
    public void removeActorToPreferences(
            Long actorID,
            @RequestHeader("Authorization") String token
    ) {

    }
}
