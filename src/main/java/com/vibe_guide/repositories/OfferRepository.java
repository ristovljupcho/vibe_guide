package com.vibe_guide.repositories;

import com.vibe_guide.entities.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface OfferRepository extends JpaRepository<Offer, UUID> {
    @Query("SELECT o FROM Offer o " +
            "WHERE o.place.id = :placeId " +
            "AND o.startDate <= :now " +
            "AND o.endDate >= :now")
    List<Offer> findDailyOffersByPlaceId(LocalDateTime now, UUID placeId);

    @Query("SELECT o FROM Offer o " +
            "WHERE o.startDate <= :now " +
            "AND o.endDate >= :now")
    List<Offer> findAllDailyOffers(LocalDateTime now);

    @Query("SELECT o " +
            "FROM Offer AS o " +
            "WHERE o.startDate > :today " +
            "AND o.place.id = :placeId")
    List<Offer> findUpcomingOffersByPlaceId(LocalDateTime today, UUID placeId);

    @Query("SELECT o " +
            "FROM Offer AS o " +
            "WHERE o.startDate > :today")
    List<Offer> findAllUpcomingOffers(LocalDateTime today);
}