package com.vibe_guide.exceptions;

import java.util.UUID;

public class UserNotFoundException extends CustomNotFoundException {
    public UserNotFoundException(String username) {
        super(String.format("User with username %s not found", username));
    }
    public UserNotFoundException(UUID userId) {
        super(String.format("User with id %s not found", userId));
    }

}
