package com.vibe_guide.entities;

import com.vibe_guide.enums.TraitType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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

import java.util.Objects;
import java.util.Set;
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

    @Enumerated(EnumType.STRING)
    private TraitType traitType;

    @Column(unique = true)
    private String traitName;

    @OneToMany(mappedBy = "trait",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private Set<PlaceTrait> places;

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