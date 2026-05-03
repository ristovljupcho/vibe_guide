package com.vibe_guide.domain.traitlike.services.impl;

import com.vibe_guide.domain.traitlike.services.TraitLikeQueryService;
import com.vibe_guide.domain.trait.dtos.TraitResponseDTO;
import com.vibe_guide.domain.traitlike.repositories.TraitLikeRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TraitLikeQueryServiceImpl implements TraitLikeQueryService {

  private final TraitLikeRepository traitLikeRepository;

  /**
   * Retrieves all traits that a given user has liked for a specific place.
   *
   * <p>This method uses a lightweight DTO projection query to avoid loading entire entity graphs,
   * ensuring optimal performance with no N+1 query issues.
   *
   * @param placeId the ID of the place whose traits are being fetched
   * @param userId the ID of the user who liked those traits
   * @return a list of {@link TraitResponseDTO} representing the liked traits
   */
  @Override
  public List<TraitResponseDTO> getAllByPlaceIdAndUserId(UUID placeId, String userId) {
    return traitLikeRepository.findAllByUserIdAndPlaceId(userId, placeId);
  }
}


