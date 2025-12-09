package com.vibe_guide.services.impl;

import com.vibe_guide.converters.PlaceConverter;
import com.vibe_guide.dtos.PlaceRequestDTO;
import com.vibe_guide.dtos.PlaceResponseDTO;
import com.vibe_guide.entities.Place;
import com.vibe_guide.enums.PriceLevel;
import com.vibe_guide.enums.PrimaryType;
import com.vibe_guide.exceptions.PlaceAlreadyExistsException;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.repositories.PlaceRepository;
import com.vibe_guide.services.PlaceManagementService;
import com.vibe_guide.utils.PlaceResponseMessages;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class PlaceManagementServiceImpl implements PlaceManagementService {
    private final PlaceRepository placeRepository;
    private final PlaceConverter placeConverter;

    /**
     * Creates a {@link Place} based on supplied DTO data.
     *
     * @param placeRequestDTO DTO carrying place attributes.
     * @return DTO of type {@link PlaceResponseDTO} representing persisted place.
     */
    @Transactional
    @Override
    public PlaceResponseDTO insertPlace(PlaceRequestDTO placeRequestDTO) {
        UUID placeId = placeRequestDTO.placeId();
        if (placeRepository.existsById(placeId)) {
            throw new PlaceAlreadyExistsException(placeId);
        }

        Place place = buildPlace(placeRequestDTO);
        Place savedPlace = placeRepository.save(place);

        return placeConverter.toPlaceResponseDTO(savedPlace);
    }

    /**
     * Updates a {@link Place} with data provided by DTO named {@link PlaceRequestDTO}.
     *
     * @param placeRequestDTO DTO of type {@link PlaceRequestDTO} containing data.
     * @return DTO of type {@link PlaceResponseDTO}.
     */
    @Transactional
    @Override
    public String updatePlace(PlaceRequestDTO placeRequestDTO) {
        UUID placeId = placeRequestDTO.placeId();
        Place place = getPlace(placeId);

        String name = placeRequestDTO.name();
        String description = placeRequestDTO.description();
        String mapsUri = placeRequestDTO.mapsUri();
        String phoneNumber = placeRequestDTO.phoneNumber();
        String address = placeRequestDTO.address();
        String menuLink = placeRequestDTO.menuLink();
        PrimaryType primaryType = placeRequestDTO.primaryType();
        PriceLevel priceLevel = placeRequestDTO.priceLevel();

        place.setName(name);
        place.setDescription(description);
        place.setMapsUri(mapsUri);
        place.setPhoneNumber(phoneNumber);
        place.setAddress(address);
        place.setMenuLink(menuLink);
        place.setPrimaryType(primaryType);
        place.setPriceLevel(priceLevel);

        placeRepository.save(place);

        return String.format(PlaceResponseMessages.PLACE_UPDATE_MESSAGE, name);
    }

    /**
     * Deletes a {@link Place} with provided ID.
     *
     * @param placeId ID of the {@link Place}.
     * @return Message of type {@link PlaceResponseMessages}.
     */
    @Transactional
    @Override
    public String deletePlace(UUID placeId) {
        Place place = getPlace(placeId);

        placeRepository.deleteById(placeId);

        String placeName = place.getName();
        return String.format(PlaceResponseMessages.PLACE_DELETE_MESSAGE, placeId, placeName);
    }

    private Place getPlace(UUID placeId) {
        return placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));
    }

    private Place buildPlace(PlaceRequestDTO placeRequestDTO) {
        Place place = new Place();
        place.setId(placeRequestDTO.placeId());
        place.setName(placeRequestDTO.name());
        place.setDescription(placeRequestDTO.description());
        place.setMapsUri(placeRequestDTO.mapsUri());
        place.setPhoneNumber(placeRequestDTO.phoneNumber());
        place.setAddress(placeRequestDTO.address());
        place.setRating(0.0);
        place.setMenuLink(placeRequestDTO.menuLink());
        place.setPrimaryType(placeRequestDTO.primaryType());
        place.setPriceLevel(placeRequestDTO.priceLevel());

        return place;
    }
}