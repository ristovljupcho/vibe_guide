package com.vibe_guide.trait.services;

import com.vibe_guide.trait.dtos.TraitInsertRequestDTO;
import com.vibe_guide.trait.dtos.TraitUpdateRequestDTO;

import java.util.UUID;

public interface TraitManagementService {

    String insertTrait(TraitInsertRequestDTO traitInsertRequestDTO);

    String updateTrait(TraitUpdateRequestDTO traitUpdateRequestDTO);

    String deleteTrait(UUID traitId);
}