package com.vibe_guide.wishlistplace.entities;

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
public class WishlistPlaceId implements Serializable {
  private UUID userId;
  private UUID placeId;
}
