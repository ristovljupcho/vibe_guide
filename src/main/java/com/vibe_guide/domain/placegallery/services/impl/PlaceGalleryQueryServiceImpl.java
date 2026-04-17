package com.vibe_guide.domain.placegallery.services.impl;

import com.vibe_guide.domain.placegallery.services.PlaceGalleryQueryService;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.domain.place.repositories.PlaceRepository;
import com.vibe_guide.domain.placegallery.entities.PlaceGallery;
import com.vibe_guide.domain.placegallery.repositories.PlaceGalleryRepository;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlaceGalleryQueryServiceImpl implements PlaceGalleryQueryService {
  private final PlaceGalleryRepository placeGalleryRepository;
  private final PlaceRepository placeRepository;

  @Override
  public List<PlaceGallery> getAllByPlaceId(UUID placeId) {
    placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));

    return placeGalleryRepository.findAllByPlaceId(placeId);
  }
}


