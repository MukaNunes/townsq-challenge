package com.townsq.challenge.api.domain;

import com.townsq.challenge.domain.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private String username;
    private String password;
    private UserRole role;
}
