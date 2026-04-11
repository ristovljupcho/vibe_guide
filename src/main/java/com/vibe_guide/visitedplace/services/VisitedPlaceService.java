package com.vibe_guide.visitedplace.services;

import com.vibe_guide.visitedplace.dtos.VisitedPlaceResponseDTO;
import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing user visited places.
 *
 * <p>This interface defines operations for:
 *
 * <ul>
 *   <li>Retrieving all places visited by a specific user
 *   <li>Toggling (adding or removing) a visited place for a user
 * </ul>
 *
 * Implementations must handle validation of user and place existence.
 */
public interface VisitedPlaceService {

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
   * @throws com.vibe_guide.exceptions.UserNotFoundException if the user does not exist
   */
  List<VisitedPlaceResponseDTO> getVisitedPlacesByUserId(UUID userId);

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
   * @throws com.vibe_guide.exceptions.UserNotFoundException if the user does not exist
   * @throws com.vibe_guide.exceptions.PlaceNotFoundException if the place does not exist
   */
  String manageVisitedPlace(UUID userId, UUID placeId);
}
