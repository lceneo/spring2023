package com.example.spring2023.Domain.services;
import com.example.spring2023.Domain.DTO.RequestDTO.AuthenticationRequest;
import com.example.spring2023.Domain.DTO.RequestDTO.RegisterRequest;
import com.example.spring2023.Domain.DTO.ResponseDTO.AuthenticationResponse;

public interface IAuthenticationService {

     /**
      *
      * @param request consisting of login and password
      * @return response consisting of token
      */
     AuthenticationResponse authenticate(AuthenticationRequest request);

     /**
      *
      * @param request consisting of user-info
      * @return response consisting of token
      */
     AuthenticationResponse register(RegisterRequest request);
}
