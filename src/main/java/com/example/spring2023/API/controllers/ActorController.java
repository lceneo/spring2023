package com.example.spring2023.API.controllers;
import com.example.spring2023.Application.utils.ErrorMessage;
import com.example.spring2023.Domain.DTO.RequestDTO.ActorFiltersRequestDTO;
import com.example.spring2023.Domain.DTO.RequestDTO.ActorRequestDTO;
import com.example.spring2023.Domain.DTO.ResponseDTO.ActorResponseDTO;
import com.example.spring2023.Domain.mappers.Response.IActorResponseDTOMapper;
import com.example.spring2023.Domain.models.User;
import com.example.spring2023.Domain.services.IActorService;
import com.example.spring2023.Domain.services.IUserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/actors")
public class ActorController {

    private final IActorService actorService;
    private final IActorResponseDTOMapper actorResponseDTOMapper;

    private final IUserService userService;

    private final ErrorMessage errorMessage;
    public ActorController(IActorService actorService,
                           IActorResponseDTOMapper actorResponseDTOMapper,
                           IUserService userService,
                           ErrorMessage errorMessage) {
        this.actorService = actorService;
        this.actorResponseDTOMapper = actorResponseDTOMapper;
        this.userService = userService;
        this.errorMessage = errorMessage;
    }

    @GetMapping
    public List<ActorResponseDTO> actors(
            @Nullable ActorFiltersRequestDTO filters,
            @RequestHeader(value = "Authorization", required = false) @Nullable String token
    ) {
        Optional<User> user = token != null ? this.userService.getUser(token.substring(7)) : Optional.empty();
        return this.actorService.getActors(filters, user)
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
    public ResponseEntity actors(
            @RequestBody @Valid ActorRequestDTO actor,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(this.errorMessage.createErrorMessage(bindingResult));
        }
        return ResponseEntity.ok(this.actorResponseDTOMapper.apply(this.actorService.createOrUpdateActor(actor)));
    }

    @DeleteMapping("/{id}")
    public void actors(
            @PathVariable Long id) {
        this.actorService.deleteActor(id);
    }
}
