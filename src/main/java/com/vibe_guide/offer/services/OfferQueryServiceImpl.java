package com.vibe_guide.offer.services;

import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.offer.dtos.OfferResponseDTO;
import com.vibe_guide.offer.entities.Offer;
import com.vibe_guide.offer.mappers.OfferMapper;
import com.vibe_guide.offer.repositories.OfferRepository;
import com.vibe_guide.place.repositories.PlaceRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OfferQueryServiceImpl implements OfferQueryService {
  private final OfferRepository offerRepository;
  private final OfferMapper offerMapper;
  private final PlaceRepository placeRepository;

  @Override
  public List<OfferResponseDTO> getActiveByPlaceId(UUID placeId) {
    checkIfPlaceExists(placeId);

    LocalDateTime now = LocalDateTime.now();

    List<Offer> offers = offerRepository.findDailyOffersByPlaceId(now, placeId);

    return offers.stream().map(offerMapper::toOfferResponseDTO).toList();
  }

  @Override
  public List<OfferResponseDTO> getActive() {
    LocalDateTime now = LocalDateTime.now();

    List<Offer> offers = offerRepository.findAllDailyOffers(now);

    return offers.stream().map(offerMapper::toOfferResponseDTO).toList();
  }

  @Override
  public List<OfferResponseDTO> getUpcoming() {
    LocalDateTime today = LocalDateTime.now();
    List<Offer> offers = offerRepository.findAllUpcomingOffers(today);

    return offers.stream().map(offerMapper::toOfferResponseDTO).toList();
  }

  @Override
  public List<OfferResponseDTO> getUpcomingByPlaceId(UUID placeId) {
    checkIfPlaceExists(placeId);

    LocalDateTime today = LocalDateTime.now();
    List<Offer> offers = offerRepository.findUpcomingOffersByPlaceId(today, placeId);

    return offers.stream().map(offerMapper::toOfferResponseDTO).toList();
  }

  void checkIfPlaceExists(UUID placeId) {
    placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));
  }
}
