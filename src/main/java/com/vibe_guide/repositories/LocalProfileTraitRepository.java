package com.vibe_guide.repositories;

import com.vibe_guide.entities.LocalProfileTrait;
import com.vibe_guide.entities.composite_keys.LocalProfileTraitId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalProfileTraitRepository extends JpaRepository<LocalProfileTrait, LocalProfileTraitId> {
}
