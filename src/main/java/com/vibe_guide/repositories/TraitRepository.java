package com.vibe_guide.repositories;

import com.vibe_guide.entities.Trait;
import com.vibe_guide.enums.TraitType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface TraitRepository extends JpaRepository<Trait, UUID> {

    @Query("select trait " +
            "from Trait trait ")
    Page<Trait> getPaginatedTraits(Pageable pageable);

    @Query("select trait " +
            "from Trait trait " +
            "where trait.traitType = :traitType")
    Page<Trait> getPaginatedTraitsByTraitType(@Param("traitType") TraitType traitType, Pageable pageable);

    Optional<Trait> getTraitByTraitTypeAndName(TraitType traitType, String name);
}
