package com.example.spring2023.Domain.DTO.RequestDTO;

import jakarta.validation.constraints.*;
import lombok.Getter;
import org.springframework.lang.Nullable;

public class ActorRequestDTO {
    @Nullable
    private Long id;

    @Getter
    @NotEmpty(message = "Name is required")
    @Size(min = 3, max = 30, message = "Name must contain from 3 to 30 characters")
    private String name;

    @Getter
    @NotEmpty(message = "Surname is required")
    @Size(min = 3, max = 30, message = "Surname must contain from 3 to 30 characters")
    private String surname;
    @Nullable
    @Size(min = 3, max = 30, message = "Patronic must contain from 3 to 30 characters")
    private String patronic;

    @Getter
    @NotNull(message = "Age is required")
    @Min(value = 1, message = "Minimum age is 0")
    @Max(value = 150, message = "Max age is 150")
    private int age;

    @Nullable
    public Long getId() {
        return id;
    }

    @Nullable
    public String getPatronic() {
        return patronic;
    }

    public ActorRequestDTO(@Nullable Long id, String name, String surname, @Nullable String patronic, int age) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronic = patronic;
        this.age = age;
    }
}

