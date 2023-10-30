package com.example.spring2023.Domain.models;

import lombok.Getter;

@Getter
public class Account {
    private Long id;
    private String login;
    private String password;

    public void setID(Long ID) {
        this.id = ID;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
