package com.example.spring2023.API.controllers;
import com.example.spring2023.Domain.DTO.ResponseDTO.UserPreferencesResponseDTO;
import com.example.spring2023.Domain.mappers.Response.IUserPreferencesResponseDTOMapper;
import com.example.spring2023.Domain.services.IUserPreferencesService;
import com.example.spring2023.Domain.services.IUserService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/profile")
public class PreferencesController {

    private final IUserService userService;
    private final IUserPreferencesService userPreferencesService;
    private final IUserPreferencesResponseDTOMapper preferencesMapper;


    public PreferencesController(IUserService userService, IUserPreferencesService userPreferencesService,
                                 IUserPreferencesResponseDTOMapper preferencesMapper) {
        this.userService = userService;
        this.userPreferencesService = userPreferencesService;
        this.preferencesMapper = preferencesMapper;
    }

    @GetMapping("/My")
    public UserPreferencesResponseDTO getMyPreferences(
            @RequestHeader("Authorization") String token
    ) {
        var user = this.userService.getUser(token.substring(7));
        return user.map(value ->
                this.preferencesMapper.apply((Optional.ofNullable(this.userPreferencesService.getMyPreferences(value)))))
                .orElse(null);
    }

    @GetMapping("/{id}")
    public Optional<UserPreferencesResponseDTO> getUserPreferences(
            @PathVariable Long id)
        {
            return Optional.ofNullable(this.preferencesMapper.apply(this.userPreferencesService.getUserPreferences(id)));
    }

    @PostMapping("/film")
    public void addFilmToPreferences(
            Long filmID,
            @RequestHeader("Authorization") String token
    ) {
        var user = this.userService.getUser(token.substring(7));
        user.ifPresent(value -> this.userPreferencesService.addFilmToPreferences(filmID, value));
    }

    @DeleteMapping("/film")
    public void removeFilmToPreferences(
            Long filmID,
            @RequestHeader("Authorization") String token
    ) {
        var user = this.userService.getUser(token.substring(7));
        user.ifPresent(value -> this.userPreferencesService.removeFilmFromPreferences(filmID, value));
    }

    @PostMapping("/actor")
    public void addActorToPreferences(
            Long actorID,
            @RequestHeader("Authorization") String token
    ) {
        var user = this.userService.getUser(token.substring(7));
        user.ifPresent(value -> this.userPreferencesService.addActorToPreferences(actorID, value));
    }

    @DeleteMapping("/actor")
    public void removeActorToPreferences(
            Long actorID,
            @RequestHeader("Authorization") String token
    ) {
        var user = this.userService.getUser(token.substring(7));
        user.ifPresent(value -> this.userPreferencesService.removeActorFromPreferences(actorID, user.get()));
    }
}
