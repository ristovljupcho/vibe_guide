package com.vibe_guide.visitedplace.entities;

import com.vibe_guide.place.entities.Place;
import com.vibe_guide.user.entities.User;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
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

  @EmbeddedId private VisitedPlaceId id;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("userId")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("placeId")
  private Place place;

  private LocalDateTime dateVisited;

  private String note;

  public VisitedPlace(User user, Place place, LocalDateTime dateVisited, String note) {
    this.id = new VisitedPlaceId(user.getId(), place.getId());
    this.user = user;
    this.place = place;
    this.dateVisited = dateVisited;
    this.note = note;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof VisitedPlace that)) return false;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
