package com.vibe_guide.domain.visitedplace.services.impl;

import com.vibe_guide.domain.visitedplace.services.VisitedPlaceService;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.exceptions.UserNotFoundException;
import com.vibe_guide.domain.place.entities.Place;
import com.vibe_guide.domain.place.repositories.PlaceRepository;
import com.vibe_guide.domain.user.entities.User;
import com.vibe_guide.domain.user.repositories.UserRepository;
import com.vibe_guide.domain.visitedplace.dtos.VisitedPlaceResponseDTO;
import com.vibe_guide.domain.visitedplace.entities.VisitedPlace;
import com.vibe_guide.domain.visitedplace.entities.VisitedPlaceId;
import com.vibe_guide.domain.visitedplace.repositories.VisitedPlaceRepository;
import com.vibe_guide.domain.visitedplace.utils.VisitedPlaceResponseMessages;
import jakarta.transaction.Transactional;
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
  public String toggle(UUID userId, UUID placeId, String note) {

    Place place =
        placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));

    User user =
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

    VisitedPlaceId visitedPlaceId = new VisitedPlaceId(userId, placeId);

    Optional<VisitedPlace> existing = visitedPlaceRepository.findById(visitedPlaceId);

    if (existing.isPresent()) {
      visitedPlaceRepository.delete(existing.get());
      return VisitedPlaceResponseMessages.VISITED_PLACE_REMOVED;
    }

    VisitedPlace visitedPlace = new VisitedPlace();
    visitedPlace.setId(visitedPlaceId);
    visitedPlace.setUser(user);
    visitedPlace.setPlace(place);
    visitedPlace.setNote(note);

    visitedPlaceRepository.save(visitedPlace);

    return VisitedPlaceResponseMessages.VISITED_PLACE_ADDED;
  }
}


