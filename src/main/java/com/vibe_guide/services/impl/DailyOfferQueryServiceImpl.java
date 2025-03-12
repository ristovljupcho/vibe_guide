package com.vibe_guide.services.impl;

import com.vibe_guide.converters.DailyOfferConverter;
import com.vibe_guide.dtos.DailyOfferResponseDTO;
import com.vibe_guide.entities.DailyOffer;
import com.vibe_guide.repositories.DailyOfferRepository;
import com.vibe_guide.services.DailyOfferQueryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DailyOfferQueryServiceImpl implements DailyOfferQueryService {
    private final DailyOfferRepository dailyOfferRepository;
    private final DailyOfferConverter dailyOfferConverter;

    /**
     * Retrieves {@link DailyOffer} objects. Filtering is enabled using placeId which will
     * display {@link DailyOffer} objects with a certain type.
     * @param placeId   uuid of the place where the dailyOffer is valid, for filtering
     *
     * @return A list of {@link DailyOffer} containing {@link DailyOfferResponseDTO} objects.
     */
    @Override
    public List<DailyOfferResponseDTO> getDailyOffers(UUID placeId) {
        List<DailyOffer> dailyOffers;

        if (placeId != null)
            dailyOffers = dailyOfferRepository.findByPlaceId(placeId);
        else
            dailyOffers = dailyOfferRepository.findAll();

        return dailyOffers.stream().map(dailyOfferConverter::toDailyOfferResponseDTO).collect(Collectors.toList());
    }
}