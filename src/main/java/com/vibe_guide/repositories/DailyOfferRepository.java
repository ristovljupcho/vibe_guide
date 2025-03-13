package com.vibe_guide.repositories;

import com.vibe_guide.entities.DailyOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface DailyOfferRepository extends JpaRepository<DailyOffer, UUID> {
    @Query("SELECT do " +
            "FROM DailyOffer AS do " +
            "WHERE do.startDate = :today " +
            "AND do.endDate >= :today " +
            "AND do.place.id = :placeId")
    List<DailyOffer> findTodayDailyOffersByPlaceId(LocalDateTime today, UUID placeId);

    @Query("SELECT do " +
            "FROM DailyOffer AS do " +
            "WHERE do.startDate = :today " +
            "AND do.endDate >= :today")
    List<DailyOffer> findTodayDailyOffers(LocalDateTime today);
}