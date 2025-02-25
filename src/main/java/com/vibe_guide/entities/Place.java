package com.vibe_guide.entities;

import com.vibe_guide.enums.PriceLevel;
import com.vibe_guide.enums.PrimaryType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Entity
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String description;

    private String mapsUri;

    private String phoneNumber;

    private String address;

    private double rating = 0.0;

    private String menuLink;

    @Enumerated(EnumType.STRING)
    private PrimaryType primaryType;

    @Enumerated(EnumType.STRING)
    private PriceLevel priceLevel;

    @OneToMany(mappedBy = "place",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private Set<PlaceAdmin> admins;

    @OneToMany(mappedBy = "place",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private Set<PlaceTrait> traits = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place that = (Place) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}