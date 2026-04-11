package com.vibe_guide.wishlistplace.services;

import com.vibe_guide.wishlistplace.dtos.WishlistPlaceResponseDTO;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.exceptions.UserNotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing a user's wishlist of places.
 * <p>
 * Provides functionality for:
 * <ul>
 *     <li>Retrieving a list of all wishlisted places for a user</li>
 *     <li>Toggling a place as wishlisted (add/remove)</li>
 * </ul>
 * <p>
 * Implementations must ensure user and place existence validation.
 */
public interface WishlistPlaceService {

    /**
     * Retrieves all wishlist places for the specified user.
     *
     * @param userId the ID of the user whose wishlist should be retrieved
     * @return a list of {@link WishlistPlaceResponseDTO}, each representing
     * a place the user has added to their wishlist
     * @throws UserNotFoundException if the user with the given ID does not exist
     */
    List<WishlistPlaceResponseDTO> getWishlistPlacesByUserId(UUID userId);

    /**
     * Toggles wishlist status for a given place:
     * <ul>
     *     <li>If the place is already in the user's wishlist ÃƒÂ¢Ã¢â‚¬Â Ã¢â‚¬â„¢ it is removed.</li>
     *     <li>If not ÃƒÂ¢Ã¢â‚¬Â Ã¢â‚¬â„¢ it is added.</li>
     * </ul>
     *
     * @param userId  the ID of the user modifying their wishlist
     * @param placeId the ID of the place to add or remove
     * @return Message describing the result (ÃƒÂ¢Ã¢â€šÂ¬Ã…â€œaddedÃƒÂ¢Ã¢â€šÂ¬Ã‚Â or ÃƒÂ¢Ã¢â€šÂ¬Ã…â€œremovedÃƒÂ¢Ã¢â€šÂ¬Ã‚Â)
     * @throws UserNotFoundException  if the user with the given ID does not exist
     * @throws PlaceNotFoundException if the place with the given ID does not exist
     */
    String manageWishlistPlace(UUID userId, UUID placeId);
}
