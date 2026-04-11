package com.vibe_guide.placegallery.services;

import com.vibe_guide.placegallery.entities.PlaceGallery;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.placegallery.repositories.PlaceGalleryRepository;
import com.vibe_guide.place.repositories.PlaceRepository;
import com.vibe_guide.placegallery.services.PlaceGalleryQueryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PlaceGalleryQueryServiceImpl implements PlaceGalleryQueryService {
    private final PlaceGalleryRepository placeGalleryRepository;
    private final PlaceRepository placeRepository;

    @Override
    public List<PlaceGallery> getPlaceGallery(UUID placeId) {
        placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));

        return placeGalleryRepository.findAllByPlaceId(placeId);
    }
}