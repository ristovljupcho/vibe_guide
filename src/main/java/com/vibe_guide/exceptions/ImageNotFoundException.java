package com.vibe_guide.exceptions;

import java.util.UUID;

public class ImageNotFoundException extends CustomNotFoundException {
    public ImageNotFoundException(UUID imageId) {
        super(String.format("Image with id %s not found", imageId));
    }
}