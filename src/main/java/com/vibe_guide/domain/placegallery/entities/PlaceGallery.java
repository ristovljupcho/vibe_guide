package com.vibe_guide.domain.placegallery.entities;

import com.vibe_guide.domain.place.entities.Place;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "place_gallery")
public class PlaceGallery {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String photo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "place_id")
  @ToString.Exclude
  private Place place;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PlaceGallery that = (PlaceGallery) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}

