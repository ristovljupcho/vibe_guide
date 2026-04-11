package com.vibe_guide.offer.services;

import com.vibe_guide.exceptions.OfferNotFoundException;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.offer.dtos.OfferInsertDTO;
import com.vibe_guide.offer.dtos.OfferUpdateDTO;
import com.vibe_guide.offer.entities.Offer;
import com.vibe_guide.offer.repositories.OfferRepository;
import com.vibe_guide.offer.utils.OfferResponseMessages;
import com.vibe_guide.place.entities.Place;
import com.vibe_guide.place.repositories.PlaceRepository;
import java.util.Base64;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class OfferManagementServiceImpl implements OfferManagementService {
  private final OfferRepository offerRepository;
  private final PlaceRepository placeRepository;

  @Override
  @Transactional
  public String insert(OfferInsertDTO offerInsertDTO) {
    UUID placeId = offerInsertDTO.placeId();
    Place place =
        placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));

    Offer offer = new Offer();
    offer.setName(offerInsertDTO.name());
    offer.setStartDate(offerInsertDTO.startDate());
    offer.setEndDate(offerInsertDTO.endDate());
    offer.setDescription(offerInsertDTO.description());
    offer.setPlace(place);

    if (offerInsertDTO.image() != null) {
      offer.setImage(Base64.getEncoder().encode(offerInsertDTO.image()));
    }

    offerRepository.save(offer);

    return OfferResponseMessages.OFFER_INSERT_MESSAGE;
  }

  @Override
  @Transactional
  public String update(OfferUpdateDTO offerUpdateDTO) {
    UUID placeId = offerUpdateDTO.placeId();
    UUID offerId = offerUpdateDTO.dailyOfferId();
    placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));
    Offer offer =
        offerRepository.findById(offerId).orElseThrow(() -> new OfferNotFoundException(offerId));

    offer.setName(offerUpdateDTO.name());
    offer.setStartDate(offerUpdateDTO.startDate());
    offer.setEndDate(offerUpdateDTO.endDate());
    offer.setDescription(offerUpdateDTO.description());

    if (offerUpdateDTO.image() != null) {
      offer.setImage(Base64.getEncoder().encode(offerUpdateDTO.image()));
    }

    offerRepository.save(offer);
    return OfferResponseMessages.OFFER_UPDATE_MESSAGE;
  }

  @Override
  @Transactional
  public String delete(UUID offerId) {
    Offer offer =
        offerRepository.findById(offerId).orElseThrow(() -> new OfferNotFoundException(offerId));

    offerRepository.delete(offer);
    return OfferResponseMessages.OFFER_DELETE_MESSAGE;
  }
}
