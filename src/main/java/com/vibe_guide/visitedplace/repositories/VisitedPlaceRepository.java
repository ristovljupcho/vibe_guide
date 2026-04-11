package com.vibe_guide.visitedplace.repositories;

import com.vibe_guide.user.entities.User;
import com.vibe_guide.visitedplace.dtos.VisitedPlaceResponseDTO;
import com.vibe_guide.visitedplace.entities.VisitedPlace;
import com.vibe_guide.visitedplace.entities.VisitedPlaceId;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VisitedPlaceRepository extends JpaRepository<VisitedPlace, VisitedPlaceId> {
  @Query(
      """
      SELECT new com.vibe_guide.visitedplace.dtos.VisitedPlaceResponseDTO(
           vp.place.id,
           vp.place.name,
           vp.place.rating,
           vp.place.description,
           vp.dateVisited,
           vp.note
      )
      FROM VisitedPlace vp
      WHERE vp.user.id = :userId
      ORDER BY vp.dateVisited DESC
      """)
  List<VisitedPlaceResponseDTO> findVisitedPlacesByUser(UUID userId);

  UUID user(User user);
}
