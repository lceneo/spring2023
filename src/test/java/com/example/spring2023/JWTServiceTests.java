package com.example.spring2023;
import com.example.spring2023.Domain.models.User;
import com.example.spring2023.Domain.services.IJWTService;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class JWTServiceTests {

    private final IJWTService jwtService;

    @Autowired
    public JWTServiceTests(IJWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Test()
    void generateToken() {
        var user = new User();
        user.setLogin("Iceneo");
        var token = this.jwtService.generateToken(user);
        Assert.isInstanceOf(String.class, token);
    }

    @Test()
    void extractLogin() {
        var user = new User();
        user.setLogin("Iceneo");
        var token = this.jwtService.generateToken(user);
        Assert.isTrue(jwtService.extractUserLogin(token).equals(user.getLogin()));
    }

    @Test()
    void extractClaim() {
        var user = new User();
        user.setLogin("Iceneo");
        Map<String, Object> claimsToAdd = new HashMap<>();
        claimsToAdd.put("testKey", "testValue");
        var token = this.jwtService.generateToken(claimsToAdd, user);
        Assert.isTrue(jwtService.extractClaim(token, (claims) -> claims.get("testKey")).equals("testValue"));
    }

    @Test()
    void extractAllClaims() {
        var user = new User();
        user.setLogin("Iceneo");
        Map<String, Object> claimsToAdd = new HashMap<>();
        claimsToAdd.put("testKey", "testValue");
        claimsToAdd.put("testKey1", "testValue1");
        var token = this.jwtService.generateToken(claimsToAdd, user);
        Assert.isTrue(jwtService.extractClaim(token, (claims) -> claims.get("testKey")).equals("testValue"));
    }
}
