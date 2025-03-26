package com.vibe_guide.exceptions;

import java.util.UUID;

public class TraitNotFoundException extends CustomNotFoundException {
    public TraitNotFoundException(UUID traitId) {
        super("Trait with id " + traitId.toString() + " not found.");
    }
}
