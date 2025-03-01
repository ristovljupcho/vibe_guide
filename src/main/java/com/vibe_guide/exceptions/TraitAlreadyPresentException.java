package com.vibe_guide.exceptions;

import com.vibe_guide.enums.TraitType;

public class TraitAlreadyPresentException extends CustomUnprocessableEntityException {
    public TraitAlreadyPresentException(TraitType traitType, String traitName) {
        super(String.format("Trait with trait type '%s' and name '%s' already present in the database.",
                traitType.name(), traitName));
    }
}
