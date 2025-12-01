package com.vibe_guide.entities.composite_keys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WishlistPlaceId implements Serializable {
    private UUID userId;
    private UUID placeId;
}