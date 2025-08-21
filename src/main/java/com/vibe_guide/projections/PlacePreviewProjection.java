package com.vibe_guide.projections;

import com.vibe_guide.enums.PrimaryType;

import java.util.UUID;

public interface PlacePreviewProjection {
    UUID getPlaceId();

    String getPlaceName();

    String getDescription();

    String getAddress();

    double getRating();

    PrimaryType getPrimaryType();

    String[] getTopTraits();
}