package com.vibe_guide.domain.favouriteplace.mappers;

import com.vibe_guide.domain.favouriteplace.entities.FavouritePlace;
import com.vibe_guide.domain.favouriteplace.entities.FavouritePlaceId;
import com.vibe_guide.domain.place.entities.Place;
import com.vibe_guide.domain.user.entities.User;
import org.springframework.stereotype.Component;

@Component
public class FavouritePlaceMapper {
  public FavouritePlace toFavouritePlace(FavouritePlaceId id, User user, Place place, String note) {
    return new FavouritePlace(id, user, place, null, note);
  }
}

