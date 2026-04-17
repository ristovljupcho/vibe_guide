package com.vibe_guide.exceptions;

import java.util.UUID;

public class OfferNotFoundException extends CustomNotFoundException {
  public OfferNotFoundException(UUID offerId) {
    super(String.format("Offer with id '%s' not found.", offerId.toString()));
  }
}

