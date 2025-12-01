package com.vibe_guide.services;

import com.vibe_guide.dtos.FavouritePlaceResponseDTO;

import java.util.List;
import java.util.UUID;

/**
 * Service defining operations for managing a user's favourite places.
 * <p>
 * Provides functionality for:
 * <ul>
 *     <li>Retrieving a list of all favourite places for a user</li>
 *     <li>Toggling a place as favourite (add/remove)</li>
 * </ul>
 * <p>
 * Implementations must ensure user and place existence validation.
 */
public interface FavouritePlaceService {

    /**
     * Retrieves all favourite places for a given user.
     *
     * @param userId ID of the user whose favourites are requested
     * @return List of favourite places mapped into {@link FavouritePlaceResponseDTO}
     */
    List<FavouritePlaceResponseDTO> getFavouritePlacesByUserId(UUID userId);

    /**
     * Toggles the favourite status for the given place and user.
     * <p>
     * If the user has already favourited the place, it is removed.
     * Otherwise, it is added as a new favourite.
     *
     * @param userId  ID of the user performing the action
     * @param placeId ID of the place being favourited or unfavourited
     * @return Message describing the result (“added” or “removed”)
     */
    String manageFavouritePlace(UUID userId, UUID placeId);
}
