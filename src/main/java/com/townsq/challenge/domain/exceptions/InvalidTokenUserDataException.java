package com.townsq.challenge.domain.exceptions;

public class InvalidTokenUserDataException extends RuntimeException {
    public InvalidTokenUserDataException() {
        super("Payload data present in your jwt is outdated. Please, generate a new one");
    }
}
