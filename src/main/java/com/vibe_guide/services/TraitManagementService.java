package com.vibe_guide.services;

import com.vibe_guide.dtos.TraitInsertRequestDTO;
import com.vibe_guide.dtos.TraitUpdateRequestDTO;

import java.util.UUID;

public interface TraitManagementService {

    String insertTrait(TraitInsertRequestDTO traitInsertRequestDTO);

    String updateTrait(TraitUpdateRequestDTO traitUpdateRequestDTO);

    String deleteTrait(UUID traitId);
}