package com.vibe_guide.exceptions;

import com.vibe_guide.enums.TraitType;

public class TraitAlreadyPresentException extends CustomUnprocessableEntityException {
    public TraitAlreadyPresentException(TraitType traitType, String traitName) {
        super("Trait with trait type " + traitType + " and name " + traitName + " already present in the database.");
    }
}
