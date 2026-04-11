package com.vibe_guide.trait.services;

import com.vibe_guide.trait.dtos.TraitInsertRequestDTO;
import com.vibe_guide.trait.dtos.TraitUpdateRequestDTO;
import java.util.UUID;

public interface TraitManagementService {

  String insert(TraitInsertRequestDTO traitInsertRequestDTO);

  String update(TraitUpdateRequestDTO traitUpdateRequestDTO);

  String delete(UUID traitId);
}
