package com.townsq.challenge.domain.exceptions;

public class MethodNotAllowedException extends RuntimeException {
    public MethodNotAllowedException() {
        super("Method not allowed");
    }
}
