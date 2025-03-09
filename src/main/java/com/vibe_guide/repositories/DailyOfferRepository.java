package com.vibe_guide.repositories;

import com.vibe_guide.entities.DailyOffer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DailyOfferRepository extends JpaRepository<DailyOffer, UUID> {
    Page<DailyOffer> findByPlaceId(UUID placeId, Pageable pageable);
}