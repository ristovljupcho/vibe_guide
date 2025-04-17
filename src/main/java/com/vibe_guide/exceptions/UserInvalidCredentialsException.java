package com.vibe_guide.exceptions;

public class UserInvalidCredentialsException extends CustomUnprocessableEntityException {
  public UserInvalidCredentialsException(String username) {
    super(String.format("Invalid credentials for user: %s", username));
  }
}
