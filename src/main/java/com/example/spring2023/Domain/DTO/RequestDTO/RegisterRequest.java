package com.example.spring2023.Domain.DTO.RequestDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String login;
    private String email;
    private String password;
    private String fistName;
    private String secondName;
    @Nullable
    private String patronic;
}
