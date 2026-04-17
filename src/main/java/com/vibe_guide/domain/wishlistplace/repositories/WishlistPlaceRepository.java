package com.vibe_guide.domain.wishlistplace.repositories;

import com.vibe_guide.domain.wishlistplace.dtos.WishlistPlaceResponseDTO;
import com.vibe_guide.domain.wishlistplace.entities.WishlistPlace;
import com.vibe_guide.domain.wishlistplace.entities.WishlistPlaceId;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WishlistPlaceRepository extends JpaRepository<WishlistPlace, WishlistPlaceId> {

  @Query(
      """
      SELECT new com.vibe_guide.domain.wishlistplace.dtos.WishlistPlaceResponseDTO(
          wp.place.id,
          wp.place.name,
          wp.place.rating,
          wp.place.description,
          wp.createdAt
      )
      FROM WishlistPlace wp
      WHERE wp.user.id = :userId
      ORDER BY wp.createdAt DESC
      """)
  List<WishlistPlaceResponseDTO> getWishlistByUserId(UUID userId);
}

