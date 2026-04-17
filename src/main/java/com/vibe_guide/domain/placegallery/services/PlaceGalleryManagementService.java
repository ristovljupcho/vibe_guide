package com.vibe_guide.domain.placegallery.services;

import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public interface PlaceGalleryManagementService {
  void insertAll(UUID placeId, List<MultipartFile> images);

  void deleteAll(UUID placeId);

  void deleteById(UUID imageId);
}

