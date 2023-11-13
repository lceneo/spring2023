package com.example.spring2023.Application.Services;

import com.example.spring2023.DAL.repositories.IUserRepository;
import com.example.spring2023.Domain.DTO.RequestDTO.AuthenticationRequest;
import com.example.spring2023.Domain.DTO.RequestDTO.RegisterRequest;
import com.example.spring2023.Domain.DTO.ResponseDTO.AuthenticationResponse;
import com.example.spring2023.Domain.models.User;
import com.example.spring2023.Domain.services.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                        )
        );
        var user = this.userRepository.findByLogin(request.getLogin())
                .orElseThrow();
        var jwtToken = this.jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .login(request.getLogin())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFistName())
                .secondName(request.getSecondName())
                .patronic(request.getPatronic())
                .build();
        this.userRepository.createUser(user);
        var jwtToken = this.jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public Optional<User> getUserByLogin(String login) {
        return this.userRepository.findByLogin(login);
    }
}
