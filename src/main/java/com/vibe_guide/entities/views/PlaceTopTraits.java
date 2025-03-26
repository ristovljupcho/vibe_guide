package com.vibe_guide.entities.views;

import com.vibe_guide.enums.PrimaryType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Immutable
@Getter
@Entity
@Table(name = "place_top_traits")
public class PlaceTopTraits {
    @Id
    @Column(name = "place_id")
    private UUID placeId;

    private String name;
    private double rating;

    @Enumerated(EnumType.STRING)
    @Column(name = "primary_type")
    private PrimaryType primaryType;

    @Column(name = "top_traits")
    private String topTraits;

    public List<String> getTopTraitsList() {
        return topTraits != null ?
                Arrays.asList(topTraits.split(", ")) :
                Collections.emptyList();
    }
}