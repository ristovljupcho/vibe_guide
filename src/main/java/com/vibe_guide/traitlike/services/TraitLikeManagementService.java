package com.vibe_guide.traitlike.services;

import com.vibe_guide.traitlike.dtos.TraitLikeRequestDTO;

public interface TraitLikeManagementService {
    String likeTraits(TraitLikeRequestDTO dto);

    String unlikeTraits(TraitLikeRequestDTO dto);
}
