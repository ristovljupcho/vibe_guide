package com.vibe_guide.services.impl;

import com.vibe_guide.dtos.WishlistPlaceResponseDTO;
import com.vibe_guide.entities.Place;
import com.vibe_guide.entities.User;
import com.vibe_guide.entities.WishlistPlace;
import com.vibe_guide.entities.composite_keys.WishlistPlaceId;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.exceptions.UserNotFoundException;
import com.vibe_guide.repositories.PlaceRepository;
import com.vibe_guide.repositories.UserRepository;
import com.vibe_guide.repositories.WishlistPlaceRepository;
import com.vibe_guide.services.WishlistPlaceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WishlistPlaceServiceImpl implements WishlistPlaceService {
    private final WishlistPlaceRepository wishlistRepository;
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;

    /**
     * Retrieves all wishlist places for the specified user.
     *
     * @param userId the ID of the user whose wishlist should be retrieved
     * @return a list of {@link WishlistPlaceResponseDTO}, each representing
     * a place the user has added to their wishlist
     * @throws UserNotFoundException if the user with the given ID does not exist
     */
    @Override
    public List<WishlistPlaceResponseDTO> getWishlistPlacesByUserId(UUID userId) {

        if (userRepository.findById(userId).isEmpty()) {
            throw new UserNotFoundException(userId);
        }

        return wishlistRepository.getWishlistByUserId(userId);
    }

    /**
     * Toggles wishlist status for a given place:
     * <ul>
     *     <li>If the place is already in the user's wishlist → it is removed.</li>
     *     <li>If not → it is added.</li>
     * </ul>
     *
     * @param userId  the ID of the user modifying their wishlist
     * @param placeId the ID of the place to add or remove
     * @return Message describing the result (“added” or “removed”)
     * @throws UserNotFoundException  if the user with the given ID does not exist
     * @throws PlaceNotFoundException if the place with the given ID does not exist
     */
    @Override
    @Transactional
    public String manageWishlistPlace(UUID userId, UUID placeId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new PlaceNotFoundException(placeId));

        WishlistPlaceId id = new WishlistPlaceId(userId, placeId);
        Optional<WishlistPlace> existing = wishlistRepository.findById(id);

        if (existing.isPresent()) {
            wishlistRepository.delete(existing.get());
            return "Place removed from wishlist.";
        }

        WishlistPlace wishlistPlace = new WishlistPlace(
                id,
                user,
                place,
                LocalDateTime.now()
        );

        wishlistRepository.save(wishlistPlace);

        return "Place added to wishlist.";
    }
}
