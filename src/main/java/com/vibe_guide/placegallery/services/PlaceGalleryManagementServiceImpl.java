package com.vibe_guide.placegallery.services;

import com.vibe_guide.exceptions.ImageNotFoundException;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.place.entities.Place;
import com.vibe_guide.place.repositories.PlaceRepository;
import com.vibe_guide.placegallery.entities.PlaceGallery;
import com.vibe_guide.placegallery.repositories.PlaceGalleryRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class PlaceGalleryManagementServiceImpl implements PlaceGalleryManagementService {
  private final PlaceRepository placeRepository;
  private final PlaceGalleryRepository placeGalleryRepository;

  @Override
  @Transactional
  public void insertAll(UUID placeId, List<MultipartFile> images) {
    Place place =
        placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));
    List<PlaceGallery> placeGallery = new ArrayList<>();

    for (MultipartFile image : images) {
      if (image != null && !image.isEmpty()) {
        if (!Objects.requireNonNull(image.getContentType()).startsWith("image")) {
          throw new IllegalArgumentException("Uploaded file is not an image");
        }
        try {
          PlaceGallery gallery = new PlaceGallery();
          gallery.setPhoto(image.getBytes());
          gallery.setPlace(place);
          placeGallery.add(gallery);
        } catch (Exception e) {
          throw new IllegalArgumentException("Failed to read image bytes", e);
        }
      }
    }
    placeGalleryRepository.saveAll(placeGallery);
  }

  @Override
  public void deleteAll(UUID placeId) {
    placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));
    List<PlaceGallery> existingGallery = placeGalleryRepository.findAllByPlaceId(placeId);
    placeGalleryRepository.deleteAll(existingGallery);
  }

  @Override
  public void deleteById(UUID imageId) {
    if (!placeGalleryRepository.existsById(imageId)) {
      throw new ImageNotFoundException(imageId);
    }
    placeGalleryRepository.deleteById(imageId);
  }
}
