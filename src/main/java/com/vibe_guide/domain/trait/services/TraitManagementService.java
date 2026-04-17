package com.vibe_guide.domain.trait.services;

import com.vibe_guide.domain.trait.dtos.TraitInsertRequestDTO;
import com.vibe_guide.domain.trait.dtos.TraitUpdateRequestDTO;
import java.util.UUID;

public interface TraitManagementService {

  String insert(TraitInsertRequestDTO traitInsertRequestDTO);

  String update(TraitUpdateRequestDTO traitUpdateRequestDTO);

  String delete(UUID traitId);
}

