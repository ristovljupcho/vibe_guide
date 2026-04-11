package com.vibe_guide.eventgallery.services;

import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public interface EventGalleryManagementService {
  void addImagesToEvent(UUID eventId, List<MultipartFile> images);

  void deleteAllImagesFromEvent(UUID eventId);

  void deleteImageFromEvent(UUID imageId);
}
