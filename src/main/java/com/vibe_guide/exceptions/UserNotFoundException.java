package com.vibe_guide.exceptions;

public class UserNotFoundException extends CustomNotFoundException {
  public UserNotFoundException(String userIdOrUsername) {
    super(String.format("User not found: %s", userIdOrUsername));
  }
}
