package com.vibe_guide.entities.views;

import com.vibe_guide.enums.TraitType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

import java.util.UUID;

@Immutable
@Getter
@Entity
@Table(name = "trait_likes_summary")
public class TraitLikesSummary {
    @Id
    private UUID id;

    private TraitType traitType;

    private String name;

    @Column(name = "total_likes")
    private Integer totalLikes;
}