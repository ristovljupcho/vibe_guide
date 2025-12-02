package com.vibe_guide.repositories;

import com.vibe_guide.dtos.VisitedPlaceResponseDTO;
import com.vibe_guide.entities.User;
import com.vibe_guide.entities.VisitedPlace;
import com.vibe_guide.entities.composite_keys.VisitedPlaceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface VisitedPlaceRepository extends JpaRepository<VisitedPlace, VisitedPlaceId> {
    @Query("""
            SELECT new com.vibe_guide.dtos.VisitedPlaceResponseDTO(
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