package com.townsq.challenge.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.townsq.challenge.domain.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @CreationTimestamp
    @JsonIgnore
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @JsonIgnore
    private LocalDateTime updatedAt;

    public User() {
    }

    public User(String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @JsonIgnore
    public List<String> getRoles() {
        List<String> roles = Stream.of(UserRole.values())
                .map(role -> role.toString())
                .collect(Collectors.toList());

        if (getRole() == UserRole.DEFAULT) {
            roles.remove(UserRole.ADMIN.toString());
            roles.remove(UserRole.ACCOUNT_MANAGER.toString());
            return roles;
        } else if (getRole() == UserRole.ACCOUNT_MANAGER) {
            roles.remove(UserRole.ADMIN.toString());
            return roles;
        }

        return roles;
    }

}
