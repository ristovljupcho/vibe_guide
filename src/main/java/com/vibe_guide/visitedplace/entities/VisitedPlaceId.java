package com.vibe_guide.visitedplace.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VisitedPlaceId implements Serializable {

    private UUID userId;
    private UUID placeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VisitedPlaceId that)) return false;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(placeId, that.placeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, placeId);
    }
}