package com.vibe_guide.domain.place.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PlaceResponseMessages {
  public static final String PLACE_UPDATE_MESSAGE = "Place with name '%s' successfully updates.";
  public static final String PLACE_DELETE_MESSAGE =
      "Place with id '%s' and name '%s' successfully deleted.";
}

