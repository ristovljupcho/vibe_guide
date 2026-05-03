package com.vibe_guide.domain.place.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Immutable
@Getter
@Entity
@Table(name = "place_top_traits")
public class PlaceTopTraits {
  @Id
  @Column(name = "place_id")
  private UUID placeId;

  @Column(name = "top_traits")
  @JdbcTypeCode(SqlTypes.ARRAY)
  private String[] topTraits;
}
