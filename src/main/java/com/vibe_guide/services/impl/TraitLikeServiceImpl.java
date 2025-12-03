package com.vibe_guide.services.impl;

import com.vibe_guide.dtos.TraitLikeRequestDTO;
import com.vibe_guide.services.TraitLikeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TraitLikeServiceImpl implements TraitLikeService {

    @Transactional
    @Override
    public String toggleLikes(TraitLikeRequestDTO dto) {

        return "";
    }
}
