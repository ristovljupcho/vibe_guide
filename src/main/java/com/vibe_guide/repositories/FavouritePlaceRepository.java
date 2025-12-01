package com.vibe_guide.repositories;

import com.vibe_guide.dtos.FavouritePlaceResponseDTO;
import com.vibe_guide.entities.FavouritePlace;
import com.vibe_guide.entities.composite_keys.FavouritePlaceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface FavouritePlaceRepository extends JpaRepository<FavouritePlace, FavouritePlaceId> {
    @Query("""
            SELECT new com.vibe_guide.dtos.FavouritePlaceResponseDTO(
                 fp.place.id,
                 fp.place.name,
                 fp.place.rating,
                 fp.place.description
            )
            FROM FavouritePlace fp
            WHERE fp.user.id = :userId
            """)
    List<FavouritePlaceResponseDTO> getFavouritePlacesByUserId(UUID userId);
}
