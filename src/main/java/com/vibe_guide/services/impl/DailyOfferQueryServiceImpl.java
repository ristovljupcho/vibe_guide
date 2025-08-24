package com.vibe_guide.services.impl;

import com.vibe_guide.converters.DailyOfferConverter;
import com.vibe_guide.dtos.DailyOfferResponseDTO;
import com.vibe_guide.entities.DailyOffer;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.repositories.DailyOfferRepository;
import com.vibe_guide.repositories.PlaceRepository;
import com.vibe_guide.services.DailyOfferQueryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DailyOfferQueryServiceImpl implements DailyOfferQueryService {
    private final DailyOfferRepository dailyOfferRepository;
    private final DailyOfferConverter dailyOfferConverter;
    private final PlaceRepository placeRepository;

    /**
     * Retrieves {@link DailyOffer} objects. Filtering is enabled using placeId and today's date, which will
     * display {@link DailyOffer} objects with a certain type.
     *
     * @param placeId uuid of the place where the dailyOffer is valid, for filtering
     * @return A list of {@link DailyOffer} containing {@link DailyOfferResponseDTO} objects.
     */
    @Override
    public List<DailyOfferResponseDTO> getTodayOffersByPlaceId(UUID placeId) {
        placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));
        LocalDateTime today = LocalDateTime.now();
        List<DailyOffer> dailyOffers = dailyOfferRepository.findDailyOffersByPlaceId(today, placeId);

        return dailyOffers.stream().map(dailyOfferConverter::toDailyOfferResponseDTO).collect(Collectors.toList());
    }

    @Override public List<DailyOfferResponseDTO> findUpcomingOffersByPlaceId(UUID placeId) {
        placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));
        LocalDateTime today = LocalDateTime.now();
        List<DailyOffer> dailyOffers = dailyOfferRepository.findUpcomingOffersByPlaceId(today, placeId);

        return dailyOffers.stream().map(dailyOfferConverter::toDailyOfferResponseDTO).collect(Collectors.toList());
    }

    /**
     * Retrieves {@link DailyOffer} objects. Filtering is enabled using today's date, which will display
     * {@link DailyOffer} objects with a certain type.
     *
     * @return A list of {@link DailyOffer} containing {@link DailyOfferResponseDTO} objects.
     */
    @Override public List<DailyOfferResponseDTO> getTodayDailyOffers() {
        LocalDateTime today = LocalDateTime.now();
        List<DailyOffer> dailyOffers = dailyOfferRepository.findAllDailyOffers(today);

        return dailyOffers.stream().map(dailyOfferConverter::toDailyOfferResponseDTO).collect(Collectors.toList());
    }
}