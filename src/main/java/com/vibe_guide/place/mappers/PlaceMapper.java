package com.vibe_guide.place.mappers;

import com.vibe_guide.enums.PriceLevel;
import com.vibe_guide.enums.PrimaryType;
import com.vibe_guide.place.dtos.PlacePreviewResponseDTO;
import com.vibe_guide.place.dtos.PlaceResponseDTO;
import com.vibe_guide.place.entities.Place;
import com.vibe_guide.place.entities.PlaceTopTraits;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class PlaceMapper {
  public PlaceResponseDTO toPlaceResponseDTO(Place place) {
    String name = place.getName();
    String description = place.getDescription();
    String mapsUri = place.getMapsUri();
    String phoneNumber = place.getPhoneNumber();
    String address = place.getAddress();
    double rating = place.getRating();
    String menuLink = place.getMenuLink();
    PrimaryType primaryType = place.getPrimaryType();
    PriceLevel priceLevel = place.getPriceLevel();
    List<String> imageUrls = place.getGallery().stream().map(gallery -> gallery.getPhoto()).toList();

    return new PlaceResponseDTO(
        name,
        description,
        mapsUri,
        phoneNumber,
        address,
        rating,
        menuLink,
        primaryType,
        priceLevel,
        imageUrls);
  }

  public PlacePreviewResponseDTO toPlacePreviewResponseDTO(PlaceTopTraits placeTopTraits) {
    UUID id = placeTopTraits.getId();
    String name = placeTopTraits.getName();
    String description = placeTopTraits.getDescription();
    double rating = placeTopTraits.getRating();
    PrimaryType primaryType = placeTopTraits.getPrimaryType();
    PriceLevel priceLevel = placeTopTraits.getPriceLevel();
    String[] topTraits = placeTopTraits.getTopTraits();

    return new PlacePreviewResponseDTO(
        id, name, description, rating, primaryType, priceLevel, topTraits);
  }
}
