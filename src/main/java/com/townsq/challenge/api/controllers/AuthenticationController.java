package com.townsq.challenge.api.controllers;

import com.townsq.challenge.api.domain.AuthenticationResponse;
import com.townsq.challenge.api.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/authenticate")
    public AuthenticationResponse authenticate(Authentication authentication) {
        return authenticationService.authenticate(authentication);
    }

}
