package com.vibe_guide.favouriteplace.mappers;

import com.vibe_guide.favouriteplace.entities.FavouritePlace;
import com.vibe_guide.favouriteplace.entities.FavouritePlaceId;
import com.vibe_guide.place.entities.Place;
import com.vibe_guide.user.entities.User;
import org.springframework.stereotype.Component;

@Component
public class FavouritePlaceMapper {
  public FavouritePlace toFavouritePlace(FavouritePlaceId id, User user, Place place) {
    return new FavouritePlace(id, user, place, null);
  }
}
