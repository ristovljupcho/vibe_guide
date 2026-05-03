package com.vibe_guide.domain.favouriteplace.repositories;

import com.vibe_guide.domain.favouriteplace.dtos.FavouritePlaceResponseDTO;
import com.vibe_guide.domain.favouriteplace.entities.FavouritePlace;
import com.vibe_guide.domain.favouriteplace.entities.FavouritePlaceId;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FavouritePlaceRepository extends JpaRepository<FavouritePlace, FavouritePlaceId> {
  @Query(
      """
      SELECT new com.vibe_guide.domain.favouriteplace.dtos.FavouritePlaceResponseDTO(
           fp.place.id,
           fp.place.name,
           fp.place.rating,
           fp.place.description,
           fp.createdAt,
           fp.note
      )
      FROM FavouritePlace fp
      WHERE fp.user.id = :userId
      ORDER BY fp.createdAt DESC
      """)
  List<FavouritePlaceResponseDTO> getAllByUserId(String userId);
}

