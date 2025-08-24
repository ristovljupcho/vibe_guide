package com.vibe_guide.services.impl;

import com.vibe_guide.dtos.DailyOfferInsertDTO;
import com.vibe_guide.dtos.DailyOfferUpdateDTO;
import com.vibe_guide.entities.DailyOffer;
import com.vibe_guide.entities.Place;
import com.vibe_guide.exceptions.DailyOfferNotFoundException;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.repositories.DailyOfferRepository;
import com.vibe_guide.repositories.PlaceRepository;
import com.vibe_guide.services.DailyOfferManagementService;
import com.vibe_guide.utils.DailyOfferResponseMessages;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.UUID;

@AllArgsConstructor
@Service
public class DailyOfferManagementServiceImpl implements DailyOfferManagementService {
    private final DailyOfferRepository dailyOfferRepository;
    private final PlaceRepository placeRepository;

    @Override
    @Transactional
    public String insertOffer(DailyOfferInsertDTO dailyOfferInsertDTO) {
        UUID placeId = dailyOfferInsertDTO.placeId();
        Place place = placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));

        DailyOffer dailyOffer = new DailyOffer();
        dailyOffer.setName(dailyOfferInsertDTO.name());
        dailyOffer.setStartDate(dailyOfferInsertDTO.startDate());
        dailyOffer.setEndDate(dailyOfferInsertDTO.endDate());
        dailyOffer.setDescription(dailyOfferInsertDTO.description());
        dailyOffer.setPlace(place);

        if (dailyOfferInsertDTO.image() != null) {
            dailyOffer.setImage(Base64.getEncoder().encode(dailyOfferInsertDTO.image()));
        }

        dailyOfferRepository.save(dailyOffer);

        return DailyOfferResponseMessages.DAILY_OFFER_INSERT_MESSAGE;
    }

    @Override
    @Transactional
    public String updateDailyOffer(DailyOfferUpdateDTO dailyOfferUpdateDTO) {
        UUID placeId = dailyOfferUpdateDTO.placeId();
        UUID dailyOfferId = dailyOfferUpdateDTO.dailyOfferId();
        placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));
        DailyOffer dailyOffer = dailyOfferRepository.findById(dailyOfferId)
                .orElseThrow(() -> new DailyOfferNotFoundException(dailyOfferId));

        dailyOffer.setName(dailyOfferUpdateDTO.name());
        dailyOffer.setStartDate(dailyOfferUpdateDTO.startDate());
        dailyOffer.setEndDate(dailyOfferUpdateDTO.endDate());
        dailyOffer.setDescription(dailyOfferUpdateDTO.description());

        if (dailyOfferUpdateDTO.image() != null) {
            dailyOffer.setImage(Base64.getEncoder().encode(dailyOfferUpdateDTO.image()));
        }

        dailyOfferRepository.save(dailyOffer);
        return DailyOfferResponseMessages.DAILY_OFFER_UPDATE_MESSAGE;
    }

    @Override
    @Transactional
    public String deleteDailyOffer(UUID dailyOfferId) {
        DailyOffer dailyOffer = dailyOfferRepository.findById(dailyOfferId)
                .orElseThrow(() -> new DailyOfferNotFoundException(dailyOfferId));

        dailyOfferRepository.delete(dailyOffer);
        return DailyOfferResponseMessages.DAILY_OFFER_DELETE_MESSAGE;
    }
}