package com.vibe_guide.services.impl;

import com.vibe_guide.dtos.DailyOfferInsertDTO;
import com.vibe_guide.dtos.DailyOfferUpdateDTO;
import com.vibe_guide.entities.DailyOffer;
import com.vibe_guide.exceptions.DailyOfferNotFoundException;
import com.vibe_guide.repositories.DailyOfferRepository;
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

    @Override
    @Transactional
    public String insertDailyOffer(DailyOfferInsertDTO dailyOfferInsertDTO) {
        DailyOffer dailyOffer = new DailyOffer();
        dailyOffer.setName(dailyOfferInsertDTO.name());
        dailyOffer.setStartDate(dailyOfferInsertDTO.startDate());
        dailyOffer.setEndDate(dailyOfferInsertDTO.endDate());
        dailyOffer.setDescription(dailyOfferInsertDTO.description());

        if (dailyOfferInsertDTO.image() != null) {
            dailyOffer.setImage(Base64.getEncoder().encode(dailyOfferInsertDTO.image()));
        }

        dailyOfferRepository.save(dailyOffer);

        return DailyOfferResponseMessages.DAILY_OFFER_INSERT_MESSAGE;
    }

    @Override
    @Transactional
    public String updateDailyOffer(UUID dailyOfferId, DailyOfferUpdateDTO dailyOfferUpdateDTO) {
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