package com.vibe_guide.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PlaceTraitResponseMessages {
    public static final String PLACE_TRAIT_INSERT_MESSAGE = "Trait successfully inserted for place with id %s.";
    public static final String PLACE_TRAIT_BATCH_INSERT_MESSAGE = "Inserted traits for place with id %s.";
    public static final String PLACE_TRAIT_UPDATE_MESSAGE = "Trait successfully updated for place with id %s.";
    public static final String PLACE_TRAIT_DELETE_MESSAGE = "Trait successfully deleted for place with id %s.";
    public static final String PLACE_TRAIT_BATCH_DELETE_MESSAGE = "Deleted %d traits for place with id %s.";
}