package com.example.spring2023.Domain.DTO.RequestDTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @NotEmpty(message = "Login is required")
    @Size(min = 4, max = 15, message = "Login must contain from 4 to 15 characters")
    private String login;

    @NotEmpty(message = "email is required")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Incorrect email format")
    private String email;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}$", message = "Password must contain from eight to 20 characters, at least one letter and one number")
    private String password;

    @NotEmpty(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name must contain from 3 to 50 characters")
    private String fistName;

    @NotEmpty(message = "Second name is required")
    @Size(min = 3, max = 50, message = "Second name must contain from 3 to 50 characters")
    private String secondName;

    @Size(min = 3, max = 50, message = "Patronic must contain from 3 to 50 characters")
    @Nullable
    private String patronic;
}
