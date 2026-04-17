package com.vibe_guide.domain.traitlike.services;

import com.vibe_guide.domain.traitlike.dtos.TraitLikeRequestDTO;

public interface TraitLikeManagementService {
  String insert(TraitLikeRequestDTO dto);

  String delete(TraitLikeRequestDTO dto);
}

