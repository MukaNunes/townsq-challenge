package com.townsq.challenge.api.services;

import com.townsq.challenge.api.domain.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private JwtService jwtService;

    public AuthenticationResponse authenticate(Authentication authentication) {
        AuthenticationResponse response = new AuthenticationResponse();
        response.setToken(jwtService.generateToken(authentication));

        return response;
    }
}