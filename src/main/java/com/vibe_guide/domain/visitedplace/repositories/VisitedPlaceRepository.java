package com.vibe_guide.domain.visitedplace.repositories;

import com.vibe_guide.domain.visitedplace.dtos.VisitedPlaceResponseDTO;
import com.vibe_guide.domain.visitedplace.entities.VisitedPlace;
import com.vibe_guide.domain.visitedplace.entities.VisitedPlaceId;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VisitedPlaceRepository extends JpaRepository<VisitedPlace, VisitedPlaceId> {
  @Query(
      """
      SELECT new com.vibe_guide.domain.visitedplace.dtos.VisitedPlaceResponseDTO(
           vp.place.id,
           vp.place.name,
           vp.place.rating,
           vp.place.description,
           vp.createdAt,
           vp.note
      )
      FROM VisitedPlace vp
      WHERE vp.user.id = :userId
      ORDER BY vp.createdAt DESC
      """)
  List<VisitedPlaceResponseDTO> findVisitedPlacesByUser(String userId);
}

