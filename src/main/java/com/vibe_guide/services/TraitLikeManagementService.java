package com.vibe_guide.services;

import com.vibe_guide.dtos.TraitLikeRequestDTO;

public interface TraitLikeManagementService {
    String likeTraits(TraitLikeRequestDTO dto);

    String unlikeTraits(TraitLikeRequestDTO dto);
}
