package com.vibe_guide.favouriteplace.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class FavouritePlaceId implements Serializable {

  @Column(name = "user_id")
  private UUID userId;

  @Column(name = "place_id")
  private UUID placeId;
}
