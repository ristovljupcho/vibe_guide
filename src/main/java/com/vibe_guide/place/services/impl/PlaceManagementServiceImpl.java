package com.vibe_guide.place.services.impl;

import com.vibe_guide.place.services.PlaceManagementService;
import com.vibe_guide.enums.PriceLevel;
import com.vibe_guide.enums.PrimaryType;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.place.dtos.PlaceRequestDTO;
import com.vibe_guide.place.dtos.PlaceResponseDTO;
import com.vibe_guide.place.entities.Place;
import com.vibe_guide.place.repositories.PlaceRepository;
import com.vibe_guide.place.utils.PlaceResponseMessages;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PlaceManagementServiceImpl implements PlaceManagementService {
  private PlaceRepository placeRepository;

  @Override
  public PlaceResponseDTO insert() {
    return null;
  }

  @Transactional
  @Override
  public String update(PlaceRequestDTO placeRequestDTO) {
    UUID placeId = placeRequestDTO.placeId();
    Place place = getById(placeId);

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

  @Transactional
  @Override
  public String delete(UUID placeId) {
    Place place = getById(placeId);

    placeRepository.deleteById(placeId);

    String placeName = place.getName();
    return String.format(PlaceResponseMessages.PLACE_DELETE_MESSAGE, placeId, placeName);
  }

  private Place getById(UUID placeId) {
    return placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));
  }
}

