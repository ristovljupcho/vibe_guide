package com.vibe_guide.services.impl;

import com.vibe_guide.entities.Place;
import com.vibe_guide.entities.PlaceGallery;
import com.vibe_guide.exceptions.ImageNotFoundException;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.repositories.PlaceGalleryRepository;
import com.vibe_guide.repositories.PlaceRepository;
import com.vibe_guide.services.PlaceGalleryManagementService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PlaceGalleryManagementServiceImpl implements PlaceGalleryManagementService {
    private final PlaceRepository placeRepository;
    private final PlaceGalleryRepository placeGalleryRepository;

    /**
     * Inserts {@link PlaceGallery} objects with provided<b><i>UUID placeId</i></b>.
     *
     * @param placeId UUID of the {@link Place} object, for which the images need to be inserted.
     * @param images  list of MultipartFile images
     */
    @Override
    @Transactional
    public void addImagesToPlace(UUID placeId, List<MultipartFile> images) {
        Place place = placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));
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

    /**
     * Deletes a {@link PlaceGallery} objects with provided <b><i>UUID placeId</i></b>.
     *
     * @param placeId UUID of the {@link Place} objects, for which all the images need to be deleted.
     */
    @Override
    public void deleteALlImagesFromPlace(UUID placeId) {
        placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));
        List<PlaceGallery> existingGallery = placeGalleryRepository.findAllByPlaceId(placeId);
        placeGalleryRepository.deleteAll(existingGallery);
    }

    /**
     * Deletes a {@link PlaceGallery} object with provided <b><i>UUID imageId</i></b>.
     *
     * @param imageId UUID of the {@link PlaceGallery} object that needs to be deleted.
     */
    @Override
    public void deleteImageFromPlace(UUID imageId) {
        if (!placeGalleryRepository.existsById(imageId)) {
            throw new ImageNotFoundException(imageId);
        }
        placeGalleryRepository.deleteById(imageId);
    }
}