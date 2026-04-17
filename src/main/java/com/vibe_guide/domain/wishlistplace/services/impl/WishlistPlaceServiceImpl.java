package com.vibe_guide.domain.wishlistplace.services.impl;

import com.vibe_guide.domain.wishlistplace.services.WishlistPlaceService;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.exceptions.UserNotFoundException;
import com.vibe_guide.domain.place.entities.Place;
import com.vibe_guide.domain.place.repositories.PlaceRepository;
import com.vibe_guide.domain.user.entities.User;
import com.vibe_guide.domain.user.repositories.UserRepository;
import com.vibe_guide.domain.wishlistplace.dtos.WishlistPlaceResponseDTO;
import com.vibe_guide.domain.wishlistplace.entities.WishlistPlace;
import com.vibe_guide.domain.wishlistplace.entities.WishlistPlaceId;
import com.vibe_guide.domain.wishlistplace.repositories.WishlistPlaceRepository;
import com.vibe_guide.domain.wishlistplace.utils.WishlistPlaceResponseMessages;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishlistPlaceServiceImpl implements WishlistPlaceService {
  private final WishlistPlaceRepository wishlistRepository;
  private final UserRepository userRepository;
  private final PlaceRepository placeRepository;

  @Override
  public List<WishlistPlaceResponseDTO> getAllByUserId(UUID userId) {

    if (userRepository.findById(userId).isEmpty()) {
      throw new UserNotFoundException(userId);
    }

    return wishlistRepository.getWishlistByUserId(userId);
  }

  @Override
  @Transactional
  public String toggle(UUID userId, UUID placeId, String note) {

    User user =
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

    Place place =
        placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));

    WishlistPlaceId id = new WishlistPlaceId(userId, placeId);
    Optional<WishlistPlace> existing = wishlistRepository.findById(id);

    if (existing.isPresent()) {
      wishlistRepository.delete(existing.get());
      return WishlistPlaceResponseMessages.WISHLIST_PLACE_REMOVED;
    }

    WishlistPlace wishlistPlace = new WishlistPlace();
    wishlistPlace.setId(id);
    wishlistPlace.setUser(user);
    wishlistPlace.setPlace(place);
    wishlistPlace.setNote(note);

    wishlistRepository.save(wishlistPlace);

    return WishlistPlaceResponseMessages.WISHLIST_PLACE_ADDED;
  }
}


