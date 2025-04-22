package com.vibe_guide.utils;

import com.vibe_guide.dtos.DailyOfferResponseDTO;
import com.vibe_guide.entities.DailyOffer;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@UtilityClass
public class DailyOfferTestData {
    public static final UUID DAILY_OFFER_ID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
    private static final String DAILY_OFFER_NAME = "Daily Offer Name 1";
    private static final LocalDateTime START_DATE = LocalDateTime.now().plusDays(1);
    private static final LocalDateTime END_DATE = LocalDateTime.now().plusDays(2);
    private static final String DAILY_OFFER_DESCRIPTION = "Description 1";
    private static final byte[] IMAGE_BYTES = "test image".getBytes();

    public List<DailyOffer> getDailyOffers() {
        DailyOffer dailyOffer1 = new DailyOffer();
        dailyOffer1.setId(DAILY_OFFER_ID);
        dailyOffer1.setName(DAILY_OFFER_NAME);
        dailyOffer1.setStartDate(START_DATE);
        dailyOffer1.setEndDate(END_DATE);
        dailyOffer1.setDescription(DAILY_OFFER_DESCRIPTION);
        dailyOffer1.setImage(IMAGE_BYTES);

        DailyOffer dailyOffer2 = new DailyOffer();
        dailyOffer2.setId(UUID.fromString("6a3e9932-4802-4815-8de8-2f1e99bdf812"));
        dailyOffer2.setName("Daily Offer 2");
        dailyOffer2.setStartDate(START_DATE);
        dailyOffer2.setEndDate(END_DATE);
        dailyOffer2.setDescription(DAILY_OFFER_DESCRIPTION);
        dailyOffer2.setImage(IMAGE_BYTES);

        DailyOffer dailyOffer3 = new DailyOffer();
        dailyOffer3.setId(UUID.fromString("6a3e9832-4802-4865-8de8-2f1e99bdf812"));
        dailyOffer3.setName("Daily Offer 3");
        dailyOffer3.setStartDate(START_DATE);
        dailyOffer3.setEndDate(END_DATE);
        dailyOffer3.setDescription(DAILY_OFFER_DESCRIPTION);
        dailyOffer3.setImage(IMAGE_BYTES);

        return List.of(dailyOffer1, dailyOffer2, dailyOffer3);
    }

    public DailyOffer getDailyOffer() {
        return getDailyOffers().getFirst();
    }

    public List<DailyOfferResponseDTO> getDailyOfferResponseDTOs() {
        DailyOfferResponseDTO dto1 = new DailyOfferResponseDTO(
                DAILY_OFFER_NAME,
                START_DATE,
                END_DATE,
                DAILY_OFFER_DESCRIPTION,
                IMAGE_BYTES
        );
        DailyOfferResponseDTO dto2 = new DailyOfferResponseDTO(
                DAILY_OFFER_NAME,
                START_DATE,
                END_DATE,
                DAILY_OFFER_DESCRIPTION,
                IMAGE_BYTES
        );
        DailyOfferResponseDTO dto3 = new DailyOfferResponseDTO(
                DAILY_OFFER_NAME,
                START_DATE,
                END_DATE,
                DAILY_OFFER_DESCRIPTION,
                IMAGE_BYTES
        );

        return List.of(dto1, dto2, dto3);
    }

    public DailyOfferResponseDTO getDailyOfferResponseDTO() {
        return getDailyOfferResponseDTOs().getFirst();
    }
}