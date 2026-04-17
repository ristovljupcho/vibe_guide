package com.vibe_guide.domain.eventgallery.services;

import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public interface EventGalleryManagementService {
  void insertAll(UUID eventId, List<MultipartFile> images);

  void deleteAll(UUID eventId);

  void deleteById(UUID imageId);
}

