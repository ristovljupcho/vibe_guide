package com.vibe_guide.exceptions;

public class UsernameAlreadyTakenException extends CustomUnprocessableEntityException {
    public UsernameAlreadyTakenException(String username) {
        super(String.format("Username %s is already taken!", username));
    }
}
