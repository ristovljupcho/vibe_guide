package com.vibe_guide.wishlistplace.services;

import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.exceptions.UserNotFoundException;
import com.vibe_guide.place.entities.Place;
import com.vibe_guide.place.repositories.PlaceRepository;
import com.vibe_guide.user.entities.User;
import com.vibe_guide.user.repositories.UserRepository;
import com.vibe_guide.wishlistplace.dtos.WishlistPlaceResponseDTO;
import com.vibe_guide.wishlistplace.entities.WishlistPlace;
import com.vibe_guide.wishlistplace.entities.WishlistPlaceId;
import com.vibe_guide.wishlistplace.repositories.WishlistPlaceRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
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
  public List<WishlistPlaceResponseDTO> getWishlistPlacesByUserId(UUID userId) {

    if (userRepository.findById(userId).isEmpty()) {
      throw new UserNotFoundException(userId);
    }

    return wishlistRepository.getWishlistByUserId(userId);
  }

  @Override
  @Transactional
  public String manageWishlistPlace(UUID userId, UUID placeId) {

    User user =
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

    Place place =
        placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));

    WishlistPlaceId id = new WishlistPlaceId(userId, placeId);
    Optional<WishlistPlace> existing = wishlistRepository.findById(id);

    if (existing.isPresent()) {
      wishlistRepository.delete(existing.get());
      return "Place removed from wishlist.";
    }

    WishlistPlace wishlistPlace = new WishlistPlace(id, user, place, LocalDateTime.now());

    wishlistRepository.save(wishlistPlace);

    return "Place added to wishlist.";
  }
}
