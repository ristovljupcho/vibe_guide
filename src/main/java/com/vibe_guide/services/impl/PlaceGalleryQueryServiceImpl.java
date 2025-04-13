package com.vibe_guide.services.impl;

import com.vibe_guide.entities.PlaceGallery;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.repositories.PlaceGalleryRepository;
import com.vibe_guide.repositories.PlaceRepository;
import com.vibe_guide.services.PlaceGalleryQueryService;
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