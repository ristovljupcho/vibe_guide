package com.vibe_guide.visitedplace.services;

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

  /**
   * Retrieves all places visited by a given user.
   *
   * <p>This method should:
   *
   * <ul>
   *   <li>Validate that the user exists
   *   <li>Return a list of mapped DTOs representing visited places
   * </ul>
   *
   * @param userId the ID of the user whose visited places should be fetched
   * @return a list of {@link VisitedPlaceResponseDTO} entries
   * @throws UserNotFoundException if the user does not exist
   */
  @Override
  public List<VisitedPlaceResponseDTO> getVisitedPlacesByUserId(UUID userId) {
    if (userRepository.findById(userId).isEmpty()) {
      throw new UserNotFoundException(userId);
    }

    return visitedPlaceRepository.findVisitedPlacesByUser(userId);
  }

  /**
   * Toggles whether a user has marked a specific place as visited.
   *
   * <p>Behavior:
   *
   * <ul>
   *   <li>If the user already marked the place as visited ÃƒÂ¢Ã¢â‚¬Â Ã¢â‚¬â„¢ remove the record
   *   <li>If not ÃƒÂ¢Ã¢â‚¬Â Ã¢â‚¬â„¢ create a new visited record
   * </ul>
   *
   * @param userId the ID of the user performing the action
   * @param placeId the ID of the place to toggle
   * @return a human-readable confirmation message
   * @throws UserNotFoundException if the user does not exist
   * @throws PlaceNotFoundException if the place does not exist
   */
  @Override
  @Transactional
  public String manageVisitedPlace(UUID userId, UUID placeId) {

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
