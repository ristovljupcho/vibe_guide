package com.vibe_guide.services.impl;

import com.vibe_guide.converters.DailyOfferConverter;
import com.vibe_guide.dtos.DailyOfferResponseDTO;
import com.vibe_guide.entities.DailyOffer;
import com.vibe_guide.repositories.DailyOfferRepository;
import com.vibe_guide.services.DailyOfferQueryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
@AllArgsConstructor
public class DailyOfferQueryServiceImpl implements DailyOfferQueryService {
    private final DailyOfferRepository dailyOfferRepository;
    private final DailyOfferConverter dailyOfferConverter;

    /**
     * Retrieves {@link DailyOffer} objects using pagination. Filtering is enabled using placeId which will
     * display {@link DailyOffer} objects with a certain type.
     * @param placeId   uuid of the place where the dailyOffer is valid, for filtering
     * @param page      page number
     * @param size      size of the page to be returned.
     * @return A {@link DailyOffer} containing {@link DailyOfferResponseDTO} objects.
     */
    @Override
    public Page<DailyOfferResponseDTO> getDailyOffers(UUID placeId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<DailyOffer> dailyOfferPage;

        if (placeId != null)
            dailyOfferPage = dailyOfferRepository.findByPlaceId(placeId, pageRequest);
        else
            dailyOfferPage = dailyOfferRepository.findAll(pageRequest);

        return dailyOfferPage.map(dailyOfferConverter::toDailyOfferResponseDTO);
    }
}