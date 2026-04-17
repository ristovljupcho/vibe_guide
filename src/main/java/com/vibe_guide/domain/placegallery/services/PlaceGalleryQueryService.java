package com.vibe_guide.domain.placegallery.services;

import com.vibe_guide.domain.placegallery.entities.PlaceGallery;
import java.util.List;
import java.util.UUID;

public interface PlaceGalleryQueryService {
  List<PlaceGallery> getAllByPlaceId(UUID placeId);
}

