package com.vibe_guide.domain.offer.mappers;

import com.vibe_guide.domain.offer.dtos.OfferResponseDTO;
import com.vibe_guide.domain.offer.entities.Offer;
import com.vibe_guide.domain.place.entities.Place;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class OfferMapper {
  public OfferResponseDTO toOfferResponseDTO(Offer offer) {
    UUID id = offer.getId();
    String name = offer.getName();
    Place place = offer.getPlace();
    String placeName = place.getName();
    String description = offer.getDescription();
    LocalDateTime startDate = offer.getStartDate();
    LocalDateTime endDate = offer.getEndDate();
    String imageUrl = offer.getImage();

    return new OfferResponseDTO(id, name, placeName, description, startDate, endDate, imageUrl);
  }
}

