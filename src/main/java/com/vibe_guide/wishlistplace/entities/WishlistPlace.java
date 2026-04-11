package com.vibe_guide.wishlistplace.entities;

import com.vibe_guide.place.entities.Place;
import com.vibe_guide.user.entities.User;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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

  @Column(name = "date_added", nullable = false)
  private LocalDateTime dateAdded;
}
