package com.vibe_guide.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface EventGalleryManagementService {
    void addImagesToEvent(UUID eventId, List<MultipartFile> images);

    void deleteAllImagesFromEvent(UUID eventId);

    void deleteImageFromEvent(UUID imageId);
}