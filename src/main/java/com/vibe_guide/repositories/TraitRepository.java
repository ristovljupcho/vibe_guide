package com.vibe_guide.repositories;

import com.vibe_guide.entities.Trait;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TraitRepository extends JpaRepository<Trait, UUID> {
}
