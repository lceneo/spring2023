
CREATE TABLE IF NOT EXISTS actors (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    surname TEXT NOT NULL,
    patronic TEXT NULL,
    age INT NOT NULL
);

CREATE TABLE IF NOT EXISTS films (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    release_year INT NOT NULL
);

CREATE TABLE IF NOT EXISTS actor_film (
     actorID INTEGER NOT NULL,
     filmID INTEGER NOT NULL,
     PRIMARY KEY(actorID, filmID)
);

CREATE TABLE IF NOT EXISTS _user (
    id SERIAL PRIMARY KEY,
    login text NOT NULL,
    password TEXT NOT NULL,
    email TEXT NOT NULL,
    firstName TEXT NOT NULL,
    secondName TEXT NOT NULL,
    patronic TEXT NULL
);