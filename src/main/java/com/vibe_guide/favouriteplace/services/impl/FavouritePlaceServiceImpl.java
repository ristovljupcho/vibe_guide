package com.vibe_guide.favouriteplace.services.impl;

import com.vibe_guide.favouriteplace.services.FavouritePlaceService;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.exceptions.UserNotFoundException;
import com.vibe_guide.favouriteplace.dtos.FavouritePlaceResponseDTO;
import com.vibe_guide.favouriteplace.entities.FavouritePlace;
import com.vibe_guide.favouriteplace.entities.FavouritePlaceId;
import com.vibe_guide.favouriteplace.repositories.FavouritePlaceRepository;
import com.vibe_guide.place.entities.Place;
import com.vibe_guide.place.repositories.PlaceRepository;
import com.vibe_guide.user.entities.User;
import com.vibe_guide.user.repositories.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavouritePlaceServiceImpl implements FavouritePlaceService {

  private final FavouritePlaceRepository favouritePlaceRepository;
  private final UserRepository userRepository;
  private final PlaceRepository placeRepository;

  public List<FavouritePlaceResponseDTO> getAllByUserId(UUID userId) {
    if (userRepository.findById(userId).isEmpty()) {
      throw new UserNotFoundException(userId);
    }

    return favouritePlaceRepository.getAllByUserId(userId);
  }

  @Override
  @Transactional
  public String toggle(UUID userId, UUID placeId) {
    User user =
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

    Place place =
        placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));

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

