package com.vibe_guide.exceptions;

public class UserAlreadyPresentException extends CustomUnprocessableEntityException {

    public UserAlreadyPresentException(String takenUsername, String takenEmail) {
        super(generateMessage(takenUsername, takenEmail));
    }

    private static String generateMessage(String takenUsername, String takenEmail) {
        if (takenUsername != null && takenEmail != null) {
            return String.format("Username '%s' and email '%s' are already taken!", takenUsername, takenEmail);
        } else if (takenUsername != null) {
            return String.format("Username '%s' is already taken!", takenUsername);
        } else {
            return String.format("Email '%s' is already taken!", takenEmail);
        }
    }

}

