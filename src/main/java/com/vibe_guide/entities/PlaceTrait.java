package com.vibe_guide.entities;

import com.vibe_guide.enums.TraitPriority;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

    private TraitPriority priority = TraitPriority.DEFAULT;

    //todo: Maybe improvement in code and db design
    // Add a boolean named likable distinguish traits that should be liked vs traits that dont need rating like parking etc.
    // This will help design on front end by not displaying all possible traits for a place.

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
