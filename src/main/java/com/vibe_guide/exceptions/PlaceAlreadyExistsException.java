package com.vibe_guide.exceptions;

import java.util.UUID;

public class PlaceAlreadyExistsException extends CustomUnprocessableEntityException {

    public PlaceAlreadyExistsException(UUID placeId) {
        super(String.format("Place with id %s already exists.", placeId));
    }
}
