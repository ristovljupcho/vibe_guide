package com.vibe_guide.exceptions;

import java.util.UUID;

public class TraitsAlreadyPresentForPlaceException extends CustomUnprocessableEntityException{
    public TraitsAlreadyPresentForPlaceException(UUID placeId, String alreadyPresentTraits) {
        super("Traits with ids " + alreadyPresentTraits + " already exists for place " + placeId + ".");
    }
}
