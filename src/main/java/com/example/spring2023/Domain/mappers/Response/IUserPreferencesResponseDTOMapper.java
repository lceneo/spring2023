package com.example.spring2023.Domain.mappers.Response;
import com.example.spring2023.Domain.DTO.ResponseDTO.UserPreferencesResponseDTO;
import com.example.spring2023.Domain.models.UserPreferences;
import java.util.Optional;

public interface IUserPreferencesResponseDTOMapper {
    UserPreferencesResponseDTO apply(Optional<UserPreferences> userPreferences);

}
