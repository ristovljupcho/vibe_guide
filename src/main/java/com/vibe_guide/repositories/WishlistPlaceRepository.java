package com.vibe_guide.repositories;

import com.vibe_guide.dtos.WishlistPlaceResponseDTO;
import com.vibe_guide.entities.WishlistPlace;
import com.vibe_guide.entities.composite_keys.WishlistPlaceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface WishlistPlaceRepository extends JpaRepository<WishlistPlace, WishlistPlaceId> {

    @Query("""
            SELECT new com.vibe_guide.dtos.WishlistPlaceResponseDTO(
                wp.place.id,
                wp.place.name,
                wp.place.rating,
                wp.place.description
            )
            FROM WishlistPlace wp
            WHERE wp.user.id = :userId
            """)
    List<WishlistPlaceResponseDTO> getWishlistByUserId(UUID userId);
}
