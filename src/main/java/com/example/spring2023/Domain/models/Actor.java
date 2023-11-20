package com.example.spring2023.Domain.models;

import lombok.Getter;
import org.springframework.lang.Nullable;

import java.util.Objects;

@Getter
public class Actor {
    /**
     * id of an actor
     */
    private Long id;
    /**
     * name of an actor
     */
    private String name;
    /**
     * surname of an actor
     */
    private String surname;
    /**
     * patronic of an actor (optional)
     */
    @Nullable
    private String patronic;
    /**
     * age of an actor
     */
    private int age;

    public Actor(Long id, String name, String surname, @Nullable String patronic, int age) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronic = patronic;
        this.age = age;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj==this) return true;
        if (obj==null || obj.getClass()!=this.getClass()) return false;
        var actor = (Actor)obj;
        return Objects.equals(this.getFullName(), actor.getFullName()) &&
                this.getAge() == actor.getAge();
    }

    /**
     * get full name of an actor
     * @return string that was concatenated of actor's surname, name and patronic (if presented)
     */
    public String getFullName() {
        return String.format("%s %s %s", this.getSurname(), this.getName(), this.getPatronic()).trim();
    }
}
