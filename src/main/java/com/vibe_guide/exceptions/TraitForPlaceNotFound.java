package com.vibe_guide.exceptions;

import java.util.UUID;

public class TraitForPlaceNotFound extends CustomNotFoundException {
    public TraitForPlaceNotFound(UUID placeId, UUID traitId) {
        super("Trait with id " + placeId + " for place with id " + traitId + " not found.");
    }
}
