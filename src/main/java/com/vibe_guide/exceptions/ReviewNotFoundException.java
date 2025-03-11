package com.vibe_guide.exceptions;

import java.util.UUID;

public class ReviewNotFoundException extends RuntimeException {
    public ReviewNotFoundException(UUID reviewId) {
        super(String.format("Review with id %s not found", reviewId));
    }
}
