package com.vibe_guide.domain.traitlike.entities;

import com.vibe_guide.enums.TraitType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

@Immutable
@Getter
@Setter
@Entity
@Table(name = "trait_likes_summary")
public class TraitLikesSummary {
  @Id private UUID id;

  private TraitType traitType;

  private String name;

  @Column(name = "total_likes")
  private Integer totalLikes;
}

