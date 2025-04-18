package com.vibe_guide.repositories;

import com.vibe_guide.entities.Trait;
import com.vibe_guide.enums.TraitType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TraitRepository extends JpaRepository<Trait, UUID> {

    Page<Trait> findAllByTraitType(TraitType traitType, Pageable pageable);

    Optional<Trait> getTraitByTraitTypeAndName(TraitType traitType, String name);
}
