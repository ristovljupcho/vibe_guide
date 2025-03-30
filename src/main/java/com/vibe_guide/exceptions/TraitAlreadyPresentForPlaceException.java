package com.vibe_guide.exceptions;

import java.util.UUID;

public class TraitAlreadyPresentForPlaceException extends CustomUnprocessableEntityException{

    public TraitAlreadyPresentForPlaceException(UUID placeId, UUID traitId) {
        super("Trait with id " + traitId + " already exists for place with id " + placeId + ".");
    }
}
