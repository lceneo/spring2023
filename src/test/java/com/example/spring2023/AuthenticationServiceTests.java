package com.example.spring2023;

import com.example.spring2023.Domain.DTO.RequestDTO.AuthenticationRequest;
import com.example.spring2023.Domain.DTO.RequestDTO.RegisterRequest;
import com.example.spring2023.Domain.DTO.ResponseDTO.AuthenticationResponse;
import com.example.spring2023.Domain.models.User;
import com.example.spring2023.Domain.services.IAuthenticationService;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AuthenticationServiceTests {

    private final IAuthenticationService authenticationService;

    @Autowired
    public AuthenticationServiceTests(IAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Test()
    void authenticate() {
        var authRequest = new AuthenticationRequest("test", "test");
        Assert.isInstanceOf(AuthenticationResponse.class, authenticationService.authenticate(authRequest));
    }

    @Test()
    void register() {
        var registerRequest = new RegisterRequest(
                "test",
                "test@mail.ru",
                "test",
                "Nikita",
                "Os'minin",
                "Borisovich"
        );
        Assert.isInstanceOf(AuthenticationResponse.class, authenticationService.register(registerRequest));

    }
}
