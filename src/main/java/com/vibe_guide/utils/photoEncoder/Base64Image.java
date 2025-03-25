package com.vibe_guide.utils.photoEncoder;

import java.util.Base64;

public interface Base64Image {
    byte[] getImage();

    default String generateBase64Image() {
        return Base64.getEncoder().encodeToString(getImage());
    }
}