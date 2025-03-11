package com.vibe_guide.exceptions;

public class MissingUsernameException extends CustomNotFoundException {
    public MissingUsernameException() {
        super("Username is required.");
    }
}
