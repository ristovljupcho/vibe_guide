package com.vibe_guide.services.impl;

import com.vibe_guide.converters.PlaceConverter;
import com.vibe_guide.dtos.PlacePreviewResponseDTO;
import com.vibe_guide.dtos.PlaceResponseDTO;
import com.vibe_guide.entities.Place;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.repositories.PlaceRepository;
import com.vibe_guide.repositories.PlaceTopTraitsRepository;
import com.vibe_guide.services.PlaceConverterWithAttributes;
import com.vibe_guide.services.PlaceQueryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PlaceQueryServiceImpl implements PlaceQueryService {

    private final PlaceRepository placeRepository;
    private final PlaceTopTraitsRepository placeTopTraitsRepository;
    private final PlaceConverterWithAttributes placeConverterWithAttributes;
    private final PlaceConverter placeConverter;

    /**
     * Retrieves {@link Place} with provided ID.
     *
     * @param placeId ID of the targeted {@link Place}.
     * @return DTO with type {@link PlaceResponseDTO}.
     */
    @Override
    public PlaceResponseDTO getPlaceById(UUID placeId) {
        Place place = placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));

        return placeConverterWithAttributes.getPlaceResponseDTO(place);
    }

    /**
     * Retrieves top {@link Place}s based on rating.
     *
     * @return List of {@link PlacePreviewResponseDTO}.
     */
    @Override
    public List<PlacePreviewResponseDTO> getTopPlaces() {

        return placeRepository.findTopPlaces()
                .stream()
                .map(placeConverter::toPlacePreviewResponseDTO)
                .toList();
    }
}