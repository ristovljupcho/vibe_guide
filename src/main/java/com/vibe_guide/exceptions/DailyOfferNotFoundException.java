package com.vibe_guide.exceptions;

import java.util.UUID;

public class DailyOfferNotFoundException extends CustomNotFoundException {
    public DailyOfferNotFoundException(UUID dailyOfferId) {
        super(String.format("Daily Offer with id '%s' not found.", dailyOfferId.toString()));
    }
}