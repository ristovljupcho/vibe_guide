package com.vibe_guide.services.impl;

import com.vibe_guide.dtos.FavouritePlaceResponseDTO;
import com.vibe_guide.entities.FavouritePlace;
import com.vibe_guide.entities.Place;
import com.vibe_guide.entities.User;
import com.vibe_guide.entities.composite_keys.FavouritePlaceId;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.exceptions.UserNotFoundException;
import com.vibe_guide.repositories.FavouritePlaceRepository;
import com.vibe_guide.repositories.PlaceRepository;
import com.vibe_guide.repositories.UserRepository;
import com.vibe_guide.services.FavouritePlaceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FavouritePlaceServiceImpl implements FavouritePlaceService {

    private final FavouritePlaceRepository favouritePlaceRepository;
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;


    /**
     * Retrieves all favourite places for a given user.
     *
     * @param userId ID of the user whose favourites are requested
     * @return List of favourite places mapped into {@link FavouritePlaceResponseDTO}
     */
    public List<FavouritePlaceResponseDTO> getFavouritePlacesByUserId(UUID userId) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new UserNotFoundException(userId);
        }

        return favouritePlaceRepository.getFavouritePlacesByUserId(userId);
    }

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
    @Override
    @Transactional
    public String manageFavouritePlace(UUID userId, UUID placeId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new PlaceNotFoundException(placeId));

        FavouritePlaceId id = new FavouritePlaceId(userId, placeId);

        Optional<FavouritePlace> existing = favouritePlaceRepository.findById(id);

        if (existing.isPresent()) {
            favouritePlaceRepository.delete(existing.get());
            return "Place removed from favourites.";
        }

        FavouritePlace fav = new FavouritePlace(id, user, place);
        favouritePlaceRepository.save(fav);

        return "Place added to favourites.";
    }
}
