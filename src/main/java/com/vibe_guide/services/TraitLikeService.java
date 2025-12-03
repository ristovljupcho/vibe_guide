package com.vibe_guide.services;

import com.vibe_guide.dtos.TraitLikeRequestDTO;

public interface TraitLikeService {
    String toggleLikes(TraitLikeRequestDTO dto);
}
