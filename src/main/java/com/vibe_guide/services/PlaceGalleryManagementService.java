package com.vibe_guide.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface PlaceGalleryManagementService {
    void addImagesToPlace(UUID placeId, List<MultipartFile> images);
    void deleteALlImagesFromPlace(UUID placeId);
    void deleteImageFromPlace(UUID imageId);
}
