package com.townsq.challenge.api.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(setterPrefix = "with")
public class UserResponse {
    private Long id;
    private String username;
    private String role;

    public UserResponse(Long id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public UserResponse(String username, String role) {
        this.username = username;
        this.role = role;
    }
}