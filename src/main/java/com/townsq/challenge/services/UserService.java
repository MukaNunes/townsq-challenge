package com.townsq.challenge.services;

import com.townsq.challenge.api.domain.UserRequest;
import com.townsq.challenge.api.domain.UserResponse;
import com.townsq.challenge.domain.enums.UserRole;
import com.townsq.challenge.domain.exceptions.UserNotFoundException;
import com.townsq.challenge.domain.models.User;
import com.townsq.challenge.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private UserResponse buildResponse(User user) {
        return UserResponse.builder()
                .withId(user.getId())
                .withUsername(user.getUsername())
                .withRole(user.getRole().toString())
                .build();
    }

    public UserResponse getById(Long id) {
        return this.buildResponse(userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new)
        );
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    public UserResponse getByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
        return buildResponse(user);
    }

    public Optional<User> getByUsernameOptional(String username) {
        return userRepository.findByUsername(username);
    }

    public UserResponse save(UserRequest user) {
        User newUser = new User(
                user.getUsername(),
                new BCryptPasswordEncoder().encode(user.getPassword()),
                UserRole.DEFAULT
        );

        var response = userRepository.save(newUser);
        return this.buildResponse(response);
    }

    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream().map(user -> new UserResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getRole().toString()
                ))
                .collect(Collectors.toList());
    }

    public UserResponse updateUser(Long id, UserRequest user) {
        User updateUser = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        updateUser.setUsername(user.getUsername());
        updateUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        updateUser.setRole(user.getRole());

        return buildResponse(userRepository.save(updateUser));
    }

    public User getByAuth(Authentication authentication) {
        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(UserNotFoundException::new);
    }
}
