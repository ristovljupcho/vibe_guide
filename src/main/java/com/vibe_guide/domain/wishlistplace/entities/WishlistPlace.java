package com.vibe_guide.domain.wishlistplace.entities;

import com.vibe_guide.domain.place.entities.Place;
import com.vibe_guide.domain.user.entities.User;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wishlist_place")
public class WishlistPlace {

  @EmbeddedId private WishlistPlaceId id;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("userId")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("placeId")
  private Place place;

  /**
   * Stores the time when the place was added to the wishlist.
   *
   * <p>This field is also the canonical wishlist-added timestamp and should be used anywhere the
   * application needs the added-at date.
   */
  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @PrePersist
  void prePersist() {
    if (createdAt == null) {
      createdAt = LocalDateTime.now();
    }
  }
}

