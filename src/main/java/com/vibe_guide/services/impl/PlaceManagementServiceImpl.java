package com.vibe_guide.services.impl;

import com.vibe_guide.dtos.PlaceResponseDTO;
import com.vibe_guide.dtos.PlaceUpdateInfoRequestDTO;
import com.vibe_guide.entities.Place;
import com.vibe_guide.enums.PriceLevel;
import com.vibe_guide.enums.PrimaryType;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.repositories.PlaceRepository;
import com.vibe_guide.services.PlaceConverterWithAttributes;
import com.vibe_guide.services.PlaceManagementService;
import com.vibe_guide.utils.PlaceResponseMessages;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class PlaceManagementServiceImpl implements PlaceManagementService {
    private PlaceRepository placeRepository;
    private PlaceConverterWithAttributes placeConverterWithAttributes;

    @Override
    public PlaceResponseDTO insertPlace() {
        return null;
    }

    /**
     * Updates a {@link Place} with data provided by DTO named {@link PlaceUpdateInfoRequestDTO}.
     *
     * @param placeUpdateInfoRequestDTO DTO of type {@link PlaceUpdateInfoRequestDTO} containing data.
     * @return DTO of type {@link PlaceResponseDTO}.
     */
    @Transactional
    @Override
    public PlaceResponseDTO updatePlace(PlaceUpdateInfoRequestDTO placeUpdateInfoRequestDTO) {
        UUID placeId = placeUpdateInfoRequestDTO.placeId();
        Place place = placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));

        String name = placeUpdateInfoRequestDTO.name();
        String description = placeUpdateInfoRequestDTO.description();
        String mapsUri = placeUpdateInfoRequestDTO.mapsUri();
        String phoneNumber = placeUpdateInfoRequestDTO.phoneNumber();
        String address = placeUpdateInfoRequestDTO.address();
        String menuLink = placeUpdateInfoRequestDTO.menuLink();
        PrimaryType primaryType = placeUpdateInfoRequestDTO.primaryType();
        PriceLevel priceLevel = placeUpdateInfoRequestDTO.priceLevel();

        place.setName(name);
        place.setDescription(description);
        place.setMapsUri(mapsUri);
        place.setPhoneNumber(phoneNumber);
        place.setAddress(address);
        place.setMenuLink(menuLink);
        place.setPrimaryType(primaryType);
        place.setPriceLevel(priceLevel);

        placeRepository.save(place);

        return placeConverterWithAttributes.getPlaceResponseDTO(place);
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
        if (!placeRepository.existsById(placeId)) {
            throw new PlaceNotFoundException(placeId);
        }

        placeRepository.deleteById(placeId);

        return String.format(PlaceResponseMessages.PLACE_DELETE_MESSAGE, placeId);
    }
}