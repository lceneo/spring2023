package com.example.spring2023.API.controllers;

import com.example.spring2023.Domain.DTO.RequestDTO.ActorRequestDTO;
import com.example.spring2023.Domain.DTO.ResponseDTO.ActorResponseDTO;
import com.example.spring2023.Domain.mappers.Response.IActorResponseDTOMapper;
import com.example.spring2023.Domain.services.IActorService;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/actors")
public class ActorController {

    private final IActorService actorService;
    private final IActorResponseDTOMapper actorResponseDTOMapper;
    public ActorController(IActorService actorService,
                           IActorResponseDTOMapper actorResponseDTOMapper) {
        this.actorService = actorService;
        this.actorResponseDTOMapper = actorResponseDTOMapper;
    }

    @GetMapping
    public List<ActorResponseDTO> actors(
            @RequestParam @Nullable String name
    ) {
        return this.actorService.getActors(name)
                .stream()
                .map(this.actorResponseDTOMapper::apply)
                .toList();
    }

    @GetMapping("/{id}")
    public ActorResponseDTO actorByID(
            @PathVariable Long id) {
        return this.actorResponseDTOMapper.apply(this.actorService.getActorByID(id));
    }
    @PostMapping
    public ActorResponseDTO actors(
            @RequestBody ActorRequestDTO actor
    ){
        return this.actorResponseDTOMapper.apply(this.actorService.createOrUpdateActor(actor));
    }

    @DeleteMapping("/{id}")
    public void actors(
            @PathVariable Long id) {
        this.actorService.deleteActor(id);
    }
}
