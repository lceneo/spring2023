package com.example.spring2023.Application.Services;

import com.example.spring2023.DAL.repositories.IUserRepository;
import com.example.spring2023.Domain.models.User;
import com.example.spring2023.Domain.services.IJWTService;
import com.example.spring2023.Domain.services.IUserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    private final IJWTService jwtService;

    private final IUserRepository userRepository;

    public UserService(IJWTService ijwtService, IUserRepository userRepository) {
        this.jwtService = ijwtService;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getUser(String token) {
        var login = this.jwtService.extractUserLogin(token);
        return this.userRepository.findByLogin(login);
    }

    public List<User> getUsers() {
        return this.userRepository.getAll();
    }
}
