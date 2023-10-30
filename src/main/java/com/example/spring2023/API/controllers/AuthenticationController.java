package com.example.spring2023.API.controllers;

import com.example.spring2023.Application.Services.AuthenticationService;
import com.example.spring2023.Domain.DTO.RequestDTO.AuthenticationRequest;
import com.example.spring2023.Domain.DTO.RequestDTO.RegisterRequest;
import com.example.spring2023.Domain.DTO.ResponseDTO.AuthenticationResponse;
import com.example.spring2023.Domain.services.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final IAuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
    @RequestBody RegisterRequest request
){
    return ResponseEntity.ok(this.authenticationService.register(request));
}
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(this.authenticationService.authenticate(request));
    }

}
