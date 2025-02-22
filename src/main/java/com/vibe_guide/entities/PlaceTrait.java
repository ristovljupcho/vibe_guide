package com.vibe_guide.entities;

import com.vibe_guide.entities.composite_keys.PlaceTraitId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Entity
@IdClass(PlaceTraitId.class)
public class PlaceTrait {
    @Id
    @ManyToOne
    @JoinColumn(name = "place_id")
    @ToString.Exclude
    private Place place;

    @Id
    @ManyToOne
    @JoinColumn(name = "trait_id")
    @ToString.Exclude
    private Trait trait;

    private String additionalInformation;

    private Integer likeCounter;

    private Boolean priority;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlaceTrait that = (PlaceTrait) o;
        return Objects.equals(place, that.place) &&
                Objects.equals(trait, that.trait);
    }

    @Override
    public int hashCode() {
        return Objects.hash(place, trait);
    }
}