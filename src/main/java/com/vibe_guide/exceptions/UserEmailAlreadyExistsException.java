package com.vibe_guide.exceptions;

public class UserEmailAlreadyExistsException extends CustomUnprocessableEntityException {
    public UserEmailAlreadyExistsException(String email) {
        super(String.format("User with email %s already exists", email));
    }
}
