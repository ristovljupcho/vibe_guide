package com.vibe_guide.repositories;

import com.vibe_guide.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID>, JpaSpecificationExecutor<Event> {
    @Query ("SELECT e " +
            "FROM Event AS e " +
            "WHERE e.place.id = :placeId " +
            "AND e.endDate <= :now " +
            "AND e.startDate >= :oneMonthAgo " +
            "ORDER BY e.startDate DESC")
    List<Event> findPastEvents (UUID placeId, LocalDateTime now, LocalDateTime oneMonthAgo);

    @Query("SELECT e " +
            "FROM Event AS e " +
            "WHERE e.place.id = :placeId " +
            "ORDER BY e.startDate DESC")
    List<Event> findEventsByPlaceId(UUID placeId);

    @Query("SELECT e " +
            "FROM Event AS e " +
            "WHERE e.place.id = :placeId " +
            "AND e.startDate <= :now " +
            "AND e.endDate >= :now")
    List<Event> findTodayEventsByPlaceId(UUID placeId, LocalDateTime now);

    @Query("SELECT e " +
            "FROM Event AS e " +
            "WHERE e.startDate >= :now ")
    List<Event> findUpcomingEvents(LocalDateTime now);
}