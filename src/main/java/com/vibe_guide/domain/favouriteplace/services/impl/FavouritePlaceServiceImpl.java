package com.vibe_guide.domain.favouriteplace.services.impl;

import com.vibe_guide.domain.favouriteplace.dtos.FavouritePlaceToggleRequestDTO;
import com.vibe_guide.domain.favouriteplace.services.FavouritePlaceService;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.exceptions.UserNotFoundException;
import com.vibe_guide.domain.favouriteplace.dtos.FavouritePlaceResponseDTO;
import com.vibe_guide.domain.favouriteplace.entities.FavouritePlace;
import com.vibe_guide.domain.favouriteplace.entities.FavouritePlaceId;
import com.vibe_guide.domain.favouriteplace.mappers.FavouritePlaceMapper;
import com.vibe_guide.domain.favouriteplace.repositories.FavouritePlaceRepository;
import com.vibe_guide.domain.favouriteplace.utils.FavouritePlaceResponseMessages;
import com.vibe_guide.domain.place.entities.Place;
import com.vibe_guide.domain.place.repositories.PlaceRepository;
import com.vibe_guide.domain.user.entities.User;
import com.vibe_guide.domain.user.repositories.UserRepository;
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
  private final FavouritePlaceMapper favouritePlaceMapper;

  public List<FavouritePlaceResponseDTO> getAllByUserId(UUID userId) {
    if (userRepository.findById(userId).isEmpty()) {
      throw new UserNotFoundException(userId);
    }

    return favouritePlaceRepository.getAllByUserId(userId);
  }

  @Override
  @Transactional
  public String toggle(FavouritePlaceToggleRequestDTO dto) {
    UUID userId = dto.userId();
    UUID placeId = dto.placeId();
    User user =
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

    Place place =
        placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));

    FavouritePlaceId id = new FavouritePlaceId(userId, placeId);

    Optional<FavouritePlace> existing = favouritePlaceRepository.findById(id);

    if (existing.isPresent()) {
      favouritePlaceRepository.delete(existing.get());
      return FavouritePlaceResponseMessages.FAVOURITE_PLACE_REMOVED;
    }

    FavouritePlace fav = favouritePlaceMapper.toFavouritePlace(id, user, place, dto.note());
    favouritePlaceRepository.save(fav);

    return FavouritePlaceResponseMessages.FAVOURITE_PLACE_ADDED;
  }
}


