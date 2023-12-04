package com.example.spring2023.API.controllers;
import com.example.spring2023.Domain.DTO.ResponseDTO.UserPreferencesResponseDTO;
import com.example.spring2023.Domain.mappers.Response.IUserPreferencesResponseDTOMapper;
import com.example.spring2023.Domain.services.IUserPreferencesService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/profile")
public class PreferencesController {

    private final IUserPreferencesService userService;
    private final IUserPreferencesResponseDTOMapper preferencesMapper;

    public PreferencesController(IUserPreferencesService userService,
                                 IUserPreferencesResponseDTOMapper preferencesMapper) {
        this.userService = userService;
        this.preferencesMapper = preferencesMapper;
    }

    @GetMapping("/My")
    public UserPreferencesResponseDTO getMyPreferences(
            @RequestHeader("Authorization") String token
    ) {
        return preferencesMapper.apply(Optional.ofNullable(this.userService.getMyPreferences(token.substring(7))));
    }

    @GetMapping("/{id}")
    public Optional<UserPreferencesResponseDTO> getUserPreferences(
            @PathVariable Long id)
        {
            return Optional.ofNullable(this.preferencesMapper.apply(this.userService.getUserPreferences(id)));
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
