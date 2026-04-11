package com.vibe_guide.traitlike.services;

import com.vibe_guide.trait.dtos.TraitResponseDTO;
import com.vibe_guide.traitlike.repositories.TraitLikeRepository;
import com.vibe_guide.traitlike.services.TraitLikeQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TraitLikeQueryServiceImpl implements TraitLikeQueryService {

    private final TraitLikeRepository traitLikeRepository;

    /**
     * Retrieves all traits that a given user has liked for a specific place.
     * <p>
     * This method uses a lightweight DTO projection query to avoid
     * loading entire entity graphs, ensuring optimal performance with
     * no N+1 query issues.
     *
     * @param placeId the ID of the place whose traits are being fetched
     * @param userId  the ID of the user who liked those traits
     * @return a list of {@link TraitResponseDTO} representing the liked traits
     */
    @Override
    public List<TraitResponseDTO> findAllByPlaceAndUser(UUID placeId, UUID userId) {
        return traitLikeRepository.findAllByUserIdAndPlaceId(userId, placeId);
    }
}
