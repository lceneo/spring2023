package com.example.spring2023.Domain.Services;

import com.example.spring2023.DAL.PostgreDb;
import com.example.spring2023.Domain.models.Actor;
import com.example.spring2023.Domain.models.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorService {
    private final PostgreDb db;
    @Autowired
    public ActorService(
            PostgreDb db) {
        this.db = db;
    }

    public Actor insertActor(Actor actor) throws Exception {
        return this.db.insertActor(actor);
    }
    public List<Actor> getActors() {
        return this.db.getActors();
    }
}
