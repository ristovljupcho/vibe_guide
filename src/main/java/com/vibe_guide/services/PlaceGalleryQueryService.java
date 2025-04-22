package com.vibe_guide.services;

import com.vibe_guide.entities.PlaceGallery;

import java.util.List;
import java.util.UUID;

public interface PlaceGalleryQueryService {
    List<PlaceGallery> getPlaceGallery(UUID placeId);
}