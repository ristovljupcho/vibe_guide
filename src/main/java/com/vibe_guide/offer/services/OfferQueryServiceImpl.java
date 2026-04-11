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

  /**
   * Retrieves {@link Offer} objects. Filtering is enabled using placeId and today's date, which
   * will display {@link Offer} objects with a certain type.
   *
   * @param placeId uuid of the place where the offer is valid, for filtering
   * @return A list of {@link Offer} containing {@link OfferResponseDTO} objects.
   */
  @Override
  public List<OfferResponseDTO> getActiveOffersByPlaceId(UUID placeId) {
    checkIfPlaceExists(placeId);

    LocalDateTime now = LocalDateTime.now();

    List<Offer> offers = offerRepository.findDailyOffersByPlaceId(now, placeId);

    return offers.stream().map(offerMapper::toOfferResponseDTO).toList();
  }

  /**
   * Retrieves {@link Offer} objects. Filtering is enabled using today's date, which will display
   * {@link Offer} objects with a certain type.
   *
   * @return A list of {@link Offer} containing {@link OfferResponseDTO} objects.
   */
  @Override
  public List<OfferResponseDTO> getAllActiveOffers() {
    LocalDateTime now = LocalDateTime.now();

    List<Offer> offers = offerRepository.findAllDailyOffers(now);

    return offers.stream().map(offerMapper::toOfferResponseDTO).toList();
  }

  /**
   * Retrieves {@link Offer} objects. Method retrieves all upcoming offers.
   *
   * @return A list of {@link Offer} containing {@link OfferResponseDTO} objects.
   */
  @Override
  public List<OfferResponseDTO> getAllUpcomingOffers() {
    LocalDateTime today = LocalDateTime.now();
    List<Offer> offers = offerRepository.findAllUpcomingOffers(today);

    return offers.stream().map(offerMapper::toOfferResponseDTO).toList();
  }

  /**
   * Retrieves {@link Offer} objects. Method retrieves all upcoming offers for a certain place.
   *
   * @param placeId uuid of the place where the offer is valid, for filtering
   * @return A list of {@link Offer} containing {@link OfferResponseDTO} objects.
   */
  @Override
  public List<OfferResponseDTO> getUpcomingOffersByPlaceId(UUID placeId) {
    checkIfPlaceExists(placeId);

    LocalDateTime today = LocalDateTime.now();
    List<Offer> offers = offerRepository.findUpcomingOffersByPlaceId(today, placeId);

    return offers.stream().map(offerMapper::toOfferResponseDTO).toList();
  }

  void checkIfPlaceExists(UUID placeId) {
    placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));
  }
}
