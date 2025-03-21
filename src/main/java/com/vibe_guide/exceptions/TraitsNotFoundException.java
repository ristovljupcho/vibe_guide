package com.vibe_guide.exceptions;

public class TraitsNotFoundException extends CustomNotFoundException {
    public TraitsNotFoundException(String missingTraitsString) {
        super("Traits with ids " + missingTraitsString + " not found.");
    }
}