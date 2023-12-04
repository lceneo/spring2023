package com.example.spring2023.Application.Mappers.Response;

import com.example.spring2023.Domain.DTO.ResponseDTO.UserPreferencesResponseDTO;
import com.example.spring2023.Domain.mappers.Response.IUserPreferencesResponseDTOMapper;
import com.example.spring2023.Domain.models.UserPreferences;
import com.example.spring2023.Domain.services.IActorService;
import com.example.spring2023.Domain.services.IFilmService;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class UserPreferencesDTOMapper implements IUserPreferencesResponseDTOMapper {
    private final IFilmService filmService;
    private final IActorService actorService;

    public UserPreferencesDTOMapper(IFilmService filmService, IActorService actorService) {
        this.filmService = filmService;
        this.actorService = actorService;
    }

    @Override
    public UserPreferencesResponseDTO apply(Optional<UserPreferences> userPreferences) {
        return userPreferences.map(preferences -> new UserPreferencesResponseDTO(
                preferences.getFavoriteFilmsIDs().stream().map(id -> this.filmService.getFilmByID(id).getKey()).toList(),
                preferences.getFavoriteActorsIDs().stream().map(this.actorService::getActorByID).toList()
        )).orElseGet(() -> new UserPreferencesResponseDTO(new ArrayList<>(), new ArrayList<>()));
    }
}
