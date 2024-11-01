package com.townsq.challenge.api.controllers;

import com.townsq.challenge.api.domain.UserRequest;
import com.townsq.challenge.api.domain.UserResponse;
import com.townsq.challenge.domain.enums.UserRole;
import com.townsq.challenge.domain.exceptions.DuplicateUsernameException;
import com.townsq.challenge.domain.exceptions.InvalidTokenUserDataException;
import com.townsq.challenge.domain.exceptions.MethodNotAllowedException;
import com.townsq.challenge.domain.models.User;
import com.townsq.challenge.repositories.UserRepository;
import com.townsq.challenge.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("hasAuthority('DEFAULT')")
    @GetMapping
    public ResponseEntity<UserResponse> getUserData(Authentication authentication) {
        return ResponseEntity.ok(userService.getByUsername(authentication.getName()));
    }

    @PreAuthorize("hasAuthority('DEFAULT')")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> findOne(
            @PathVariable Long id,
            @RequestBody UserRequest user,
            Authentication authentication
    ) {
        User currentUser = userService.getByUsernameOptional(authentication.getName())
                .orElseThrow(InvalidTokenUserDataException::new);

        if (!Objects.equals(currentUser.getRole(), UserRole.ADMIN.toString())
                && !Objects.equals(currentUser.getId(), id)) {
            throw new MethodNotAllowedException();
        }

        UserResponse updateUser = userService.updateUser(id, user);
        return ResponseEntity.ok().body(updateUser);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    public List<UserResponse> findAll() {
        return userService.findAll();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<UserResponse> insert(@RequestBody UserRequest user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) throw new DuplicateUsernameException();

        UserResponse newUser = userService.save(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newUser);
    }
}
