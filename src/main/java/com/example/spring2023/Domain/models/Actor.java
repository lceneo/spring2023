package com.example.spring2023.Domain.models;

import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
public class Actor {
    private Long id;
    private String name;
    private String surname;
    @Nullable
    private String patronic;
    private int age;

    public Actor(Long id, String name, String surname, @Nullable String patronic, int age) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronic = patronic;
        this.age = age;
    }
}
