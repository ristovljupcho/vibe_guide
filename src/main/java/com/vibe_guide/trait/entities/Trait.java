package com.vibe_guide.trait.entities;

import com.vibe_guide.enums.TraitType;
import com.vibe_guide.placetrait.entities.PlaceTrait;
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
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
public class Trait {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @ToString.Include
  private UUID id;

  @Enumerated(EnumType.STRING)
  private TraitType traitType;

  @Column(unique = true)
  @ToString.Include
  private String name;

  @OneToMany(mappedBy = "trait", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @ToString.Exclude
  private Set<PlaceTrait> places = new HashSet<>();

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
