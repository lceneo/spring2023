package com.example.spring2023.API;

import com.example.spring2023.Domain.Services.ActorService;
import com.example.spring2023.Domain.models.Actor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ActorController {

    private final ActorService actorService;
    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping("/actors")
    public List<Actor> actors() {
        return this.actorService.getActors();
    }

    @PostMapping("/actors")
    public Actor actors(
            @RequestBody Actor actor
    ) throws Exception {
        return this.actorService.insertActor(actor);
    }
}
