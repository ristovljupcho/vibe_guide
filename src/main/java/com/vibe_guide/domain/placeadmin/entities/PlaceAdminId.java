package com.vibe_guide.domain.placeadmin.entities;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlaceAdminId implements Serializable {
  private String userId;
  private UUID placeId;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PlaceAdminId that = (PlaceAdminId) o;
    return Objects.equals(userId, that.userId) && Objects.equals(placeId, that.placeId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, placeId);
  }
}

