package com.example.spring2023.API.controllers;

import com.example.spring2023.Application.Services.AuthenticationService;
import com.example.spring2023.Application.utils.ErrorMessage;
import com.example.spring2023.Domain.DTO.RequestDTO.AuthenticationRequest;
import com.example.spring2023.Domain.DTO.RequestDTO.RegisterRequest;
import com.example.spring2023.Domain.DTO.ResponseDTO.AuthenticationResponse;
import com.example.spring2023.Domain.models.Actor;
import com.example.spring2023.Domain.services.IAuthenticationService;
import com.example.spring2023.Domain.services.IMailSender;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final IAuthenticationService authenticationService;

    private final ErrorMessage errorMessage;

    @PostMapping("/register")
    public ResponseEntity register(
    @RequestBody @Valid RegisterRequest request,
    BindingResult bindingResult
){
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(this.errorMessage.createErrorMessage(bindingResult));
        }
    return ResponseEntity.ok(this.authenticationService.register(request));
}
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(this.authenticationService.authenticate(request));
    }

}
