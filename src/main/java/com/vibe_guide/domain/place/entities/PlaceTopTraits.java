package com.vibe_guide.domain.place.entities;

import com.vibe_guide.enums.PriceLevel;
import com.vibe_guide.enums.PrimaryType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
  @Id private UUID id;

  private String name;
  private String description;
  private double rating;

  @Enumerated(EnumType.STRING)
  @Column(name = "primary_type")
  private PrimaryType primaryType;

  @Enumerated(EnumType.STRING)
  @Column(name = "price_level")
  private PriceLevel priceLevel;

  @Column(name = "top_traits")
  @JdbcTypeCode(SqlTypes.ARRAY)
  private String[] topTraits;
}

