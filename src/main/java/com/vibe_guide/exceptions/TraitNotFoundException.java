package com.vibe_guide.exceptions;

import com.vibe_guide.enums.TraitType;

import java.util.UUID;

public class TraitNotFoundException extends CustomNotFoundException {
    public TraitNotFoundException(TraitType traitType, String traitName) {
        super(String.format("Trait with trait type '%s' and name '%s' not found.", traitType.name(), traitName));
    }

    public TraitNotFoundException(UUID traitId) {
        super(String.format("Trait with id '%s' not found.", traitId.toString()));
    }
}
