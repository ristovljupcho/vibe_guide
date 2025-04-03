package com.vibe_guide.exceptions;

import java.util.UUID;

public class AdminForPlaceAlreadyExistsException extends CustomUnprocessableEntityException {
    public AdminForPlaceAlreadyExistsException(UUID placeId, UUID userId) {
        super("Admin already exists for place id " + placeId + " and user id " + userId + ".");
    }
}