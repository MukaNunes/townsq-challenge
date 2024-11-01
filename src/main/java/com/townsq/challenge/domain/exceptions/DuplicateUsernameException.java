package com.townsq.challenge.domain.exceptions;

public class DuplicateUsernameException extends RuntimeException {
    public DuplicateUsernameException() {
        super("Username already exists");
    }
}
