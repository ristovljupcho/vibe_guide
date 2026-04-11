package com.vibe_guide.favouriteplace.services;

import com.vibe_guide.favouriteplace.dtos.FavouritePlaceResponseDTO;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.exceptions.UserNotFoundException;

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
     * @throws UserNotFoundException if the user with the given ID does not exist
     */
    List<FavouritePlaceResponseDTO> getFavouritePlacesByUserId(UUID userId);

    /**
     * Toggles favourite status for a given place:
     * <ul>
     *     <li>If the place is already in the user's favourites ÃƒÂ¢Ã¢â‚¬Â Ã¢â‚¬â„¢ it is removed.</li>
     *     <li>If not ÃƒÂ¢Ã¢â‚¬Â Ã¢â‚¬â„¢ it is added.</li>
     * </ul>
     *
     * @param userId  ID of the user performing the action
     * @param placeId ID of the place being favourited or unfavourited
     * @return Message describing the result (ÃƒÂ¢Ã¢â€šÂ¬Ã…â€œaddedÃƒÂ¢Ã¢â€šÂ¬Ã‚Â or ÃƒÂ¢Ã¢â€šÂ¬Ã…â€œremovedÃƒÂ¢Ã¢â€šÂ¬Ã‚Â)
     * @throws UserNotFoundException  if the user with the given ID does not exist
     * @throws PlaceNotFoundException if the place with the given ID does not exist
     */
    String manageFavouritePlace(UUID userId, UUID placeId);
}
