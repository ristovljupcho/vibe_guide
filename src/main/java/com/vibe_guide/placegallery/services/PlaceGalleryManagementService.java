package com.vibe_guide.placegallery.services;

import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public interface PlaceGalleryManagementService {
  void addImagesToPlace(UUID placeId, List<MultipartFile> images);

  void deleteALlImagesFromPlace(UUID placeId);

  void deleteImageFromPlace(UUID imageId);
}
