package com.vibe_guide.domain.placegallery.services;

import com.vibe_guide.domain.place.entities.Place;
import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public interface PlaceGalleryManagementService {
  void insertAll(Place place, List<MultipartFile> images);

  void insertAll(UUID placeId, List<MultipartFile> images);

  void deleteAll(Place place);

  void deleteAll(UUID placeId);

  void deleteById(UUID imageId);
}

