package com.vibe_guide.traitlike.services;

import com.vibe_guide.traitlike.dtos.TraitLikeRequestDTO;

public interface TraitLikeManagementService {
  String insert(TraitLikeRequestDTO dto);

  String delete(TraitLikeRequestDTO dto);
}
