package com.vibe_guide.wishlistplace.repositories;

import com.vibe_guide.wishlistplace.dtos.WishlistPlaceResponseDTO;
import com.vibe_guide.wishlistplace.entities.WishlistPlace;
import com.vibe_guide.wishlistplace.entities.WishlistPlaceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface WishlistPlaceRepository extends JpaRepository<WishlistPlace, WishlistPlaceId> {

    @Query("""
            SELECT new com.vibe_guide.wishlistplace.dtos.WishlistPlaceResponseDTO(
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
