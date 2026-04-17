package com.vibe_guide.domain.favouriteplace.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof FavouritePlaceId that)) return false;
    return Objects.equals(userId, that.userId) && Objects.equals(placeId, that.placeId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, placeId);
  }
}

