package com.vibe_guide.trait.repositories;

import com.vibe_guide.enums.TraitType;
import com.vibe_guide.trait.entities.Trait;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TraitRepository extends JpaRepository<Trait, UUID> {

  Page<Trait> findAllByTraitType(TraitType traitType, Pageable pageable);

  Optional<Trait> getTraitByTraitTypeAndName(TraitType traitType, String name);
}
