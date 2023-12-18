package com.example.spring2023.Domain.DTO.RequestDTO;

import lombok.Getter;
import org.springframework.lang.Nullable;

public class ActorRequestDTO {
    @Nullable
    private Long id;
    @Getter
    private String name;
    @Getter
    private String surname;
    @Nullable
    private String patronic;
    @Getter
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

