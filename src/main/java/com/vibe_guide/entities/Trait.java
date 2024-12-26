package com.vibe_guide.entities;

import com.vibe_guide.enums.Atmosphere;
import com.vibe_guide.enums.DressCode;
import com.vibe_guide.enums.MusicType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Entity
public class Trait {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private boolean alcoholicBeverage;

    private boolean cocktails;

    private boolean beer;

    private boolean wine;

    private boolean coffee;

    private boolean servesBreakfast;

    private boolean servesLunch;

    private boolean servesDinner;

    private boolean servesDessert;

    private boolean vegetarianFood;

    private boolean petFriendly;

    private boolean outdoorSeating;

    private boolean paidParking;

    private boolean liveMusic;

    @Enumerated(EnumType.STRING)
    private DressCode dressCode;

    @Enumerated(EnumType.STRING)
    private Atmosphere atmosphere;

    @Enumerated(EnumType.STRING)
    private MusicType musicType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trait trait = (Trait) o;
        return Objects.equals(id, trait.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
