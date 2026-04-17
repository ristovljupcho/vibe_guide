package com.vibe_guide.visitedplace.services.impl;

import com.vibe_guide.visitedplace.services.VisitedPlaceService;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.exceptions.UserNotFoundException;
import com.vibe_guide.place.entities.Place;
import com.vibe_guide.place.repositories.PlaceRepository;
import com.vibe_guide.user.entities.User;
import com.vibe_guide.user.repositories.UserRepository;
import com.vibe_guide.visitedplace.dtos.VisitedPlaceResponseDTO;
import com.vibe_guide.visitedplace.entities.VisitedPlace;
import com.vibe_guide.visitedplace.entities.VisitedPlaceId;
import com.vibe_guide.visitedplace.repositories.VisitedPlaceRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VisitedPlaceServiceImpl implements VisitedPlaceService {

  private final VisitedPlaceRepository visitedPlaceRepository;
  private final UserRepository userRepository;
  private final PlaceRepository placeRepository;

  @Override
  public List<VisitedPlaceResponseDTO> getAllByUserId(UUID userId) {
    if (userRepository.findById(userId).isEmpty()) {
      throw new UserNotFoundException(userId);
    }

    return visitedPlaceRepository.findVisitedPlacesByUser(userId);
  }

  @Override
  @Transactional
  public String toggle(UUID userId, UUID placeId) {

    Place place =
        placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));

    User user =
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

    VisitedPlaceId visitedPlaceId = new VisitedPlaceId(userId, placeId);

    Optional<VisitedPlace> existing = visitedPlaceRepository.findById(visitedPlaceId);

    if (existing.isPresent()) {
      visitedPlaceRepository.delete(existing.get());
      return "Place removed from visited list.";
    }

    VisitedPlace visitedPlace = new VisitedPlace(user, place, LocalDateTime.now(), null);

    visitedPlaceRepository.save(visitedPlace);

    return "Place marked as visited.";
  }
}

