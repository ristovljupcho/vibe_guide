package com.vibe_guide.entities.composite_keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
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
@Embeddable
public class FavouritePlaceId implements Serializable {

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "place_id")
    private UUID placeId;
}
