package com.vibe_guide.visitedplace.entities;

import com.vibe_guide.place.entities.Place;
import com.vibe_guide.user.entities.User;
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
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "visited_place")
public class VisitedPlace {

    @EmbeddedId
    private VisitedPlaceId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("placeId")
    private Place place;

    /**
     * Stores the time when the place was marked as visited.
     *
     * <p>This field is also the canonical visit timestamp and should be used anywhere the
     * application needs the visited-at date.
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    private String note;

    @PrePersist
    void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}
