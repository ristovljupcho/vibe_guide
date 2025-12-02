package com.vibe_guide.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@ToString
public class PlaceTrait {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String additionalInformation;

    private int likeCounter = 0;

    private boolean priority = false;

    @ManyToOne
    @JoinColumn(name = "place_id", nullable = false)
    @ToString.Exclude
    private Place place;

    @ManyToOne
    @JoinColumn(name = "trait_id", nullable = false)
    @ToString.Exclude
    private Trait trait;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceTrait that = (PlaceTrait) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
