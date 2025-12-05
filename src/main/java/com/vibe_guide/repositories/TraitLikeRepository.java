package com.vibe_guide.repositories;

import com.vibe_guide.dtos.TraitResponseDTO;
import com.vibe_guide.entities.TraitLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TraitLikeRepository extends JpaRepository<TraitLike, UUID> {
    List<TraitLike> findAllByUserIdAndPlaceTraitIdIn(UUID userId, List<UUID> placeTraitIds);

    @Query("""
            SELECT new com.vibe_guide.dtos.TraitResponseDTO(
                tl.placeTrait.trait.id,
                tl.placeTrait.trait.traitType,
                tl.placeTrait.trait.name
            )
            FROM TraitLike tl
            WHERE tl.user.id = :userId AND tl.placeTrait.place.id = :placeId
            """)
    List<TraitResponseDTO> findAllByUserIdAndPlaceId(UUID userId, UUID placeId);
}
