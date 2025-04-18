package com.vibe_guide.exceptions;

import java.util.UUID;

public class PlaceNotFoundException extends CustomNotFoundException {
    public PlaceNotFoundException(String name) {
        super(String.format("Place with name %s not found.", name));
    }

    public PlaceNotFoundException(UUID placeId) {
        super(String.format("Place with id %s not found.", placeId));
    }
}