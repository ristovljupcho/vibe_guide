package com.vibe_guide.repositories;

import com.vibe_guide.entities.TraitLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TraitLikeRepository extends JpaRepository<TraitLike, UUID> {
}
