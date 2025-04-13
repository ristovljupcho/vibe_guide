package com.vibe_guide.exceptions;

import java.util.UUID;

public class AdminForPlaceNotFound extends CustomNotFoundException {
    public AdminForPlaceNotFound(UUID placeId, UUID userId) {
        super("Admin for place id " + placeId + " and user id " + userId + ".");
    }
}