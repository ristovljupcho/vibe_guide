package com.vibe_guide.exceptions;

import java.util.UUID;

public class PlaceNotFoundException extends CustomNotFoundException {

    public PlaceNotFoundException(UUID placeId) {
        super(String.format("Place with id '%s' not found.", placeId.toString()));
    }
}
