package com.vibe_guide.domain.placegallery.services.impl;

import com.vibe_guide.domain.placegallery.services.PlaceGalleryManagementService;
import com.vibe_guide.exceptions.ImageNotFoundException;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.domain.place.entities.Place;
import com.vibe_guide.domain.place.repositories.PlaceRepository;
import com.vibe_guide.domain.placegallery.entities.PlaceGallery;
import com.vibe_guide.domain.placegallery.repositories.PlaceGalleryRepository;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.vibe_guide.storage.ImageStorageService;

@Service
@AllArgsConstructor
public class PlaceGalleryManagementServiceImpl implements PlaceGalleryManagementService {
  private final PlaceRepository placeRepository;
  private final PlaceGalleryRepository placeGalleryRepository;
  private final ImageStorageService imageStorageService;

  @Override
  @Transactional
  public void insertAll(UUID placeId, List<MultipartFile> images) {
    Place place =
        placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));
    List<PlaceGallery> placeGallery =
        images.stream()
            .filter(image -> image != null && !image.isEmpty())
            .map(
                image -> {
                  PlaceGallery gallery = new PlaceGallery();
                  gallery.setPhoto(imageStorageService.store(image, "places/" + placeId));
                  gallery.setPlace(place);
                  return gallery;
                })
            .toList();

    placeGalleryRepository.saveAll(placeGallery);
  }

  @Override
  public void deleteAll(UUID placeId) {
    placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));
    List<PlaceGallery> existingGallery = placeGalleryRepository.findAllByPlaceId(placeId);
    existingGallery.forEach(gallery -> imageStorageService.delete(gallery.getPhoto()));
    placeGalleryRepository.deleteAll(existingGallery);
  }

  @Override
  public void deleteById(UUID imageId) {
    PlaceGallery gallery =
        placeGalleryRepository.findById(imageId).orElseThrow(() -> new ImageNotFoundException(imageId));
    imageStorageService.delete(gallery.getPhoto());
    placeGalleryRepository.delete(gallery);
  }
}


