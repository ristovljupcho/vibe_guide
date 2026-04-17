package com.vibe_guide.domain.trait.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TraitResponseMessages {
  public static final String TRAIT_INSERT_MESSAGE =
      "Trait of type %s and with name %s successfully inserted.";
  public static final String TRAIT_UPDATE_MESSAGE = "Trait with id %s successfully updated.";
  public static final String TRAIT_DELETE_MESSAGE = "Trait with id %s successfully deleted.";
}

