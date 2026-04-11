package com.vibe_guide.event.repositories;

import com.vibe_guide.event.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID>, JpaSpecificationExecutor<Event> {
    @Override
    @EntityGraph(attributePaths = {"place", "galleries"})
    Page<Event> findAll(Specification<Event> spec, Pageable pageable);

    @EntityGraph(attributePaths = {"place", "galleries"})
    @Query("SELECT e " +
            "FROM Event AS e " +
            "WHERE e.place.id = :placeId " +
            "AND e.endDate <= :now " +
            "AND e.startDate >= :oneMonthAgo " +
            "ORDER BY e.startDate DESC")
    List<Event> findPastEvents(UUID placeId, LocalDateTime now, LocalDateTime oneMonthAgo);

    @EntityGraph(attributePaths = {"place", "galleries"})
    @Query("SELECT e " +
            "FROM Event AS e " +
            "WHERE e.startDate >= :now ")
    List<Event> findUpcomingEvents(LocalDateTime now);

    @EntityGraph(attributePaths = {"place", "galleries"})
    @Query("SELECT e " +
            "FROM Event AS e " +
            "WHERE e.place.id = :placeId " +
            "ORDER BY e.startDate DESC")
    List<Event> findUpcomingEventsByPlaceId(UUID placeId);

    @EntityGraph(attributePaths = {"place", "galleries"})
    @Query("SELECT e " +
            "FROM Event AS e " +
            "WHERE e.startDate <= :now " +
            "AND e.endDate >= :now")
    List<Event> findActiveEvents(LocalDateTime now);

    @EntityGraph(attributePaths = {"place", "galleries"})
    @Query("SELECT e " +
            "FROM Event AS e " +
            "WHERE e.place.id = :placeId " +
            "AND e.startDate <= :now " +
            "AND e.endDate >= :now")
    List<Event> findActiveEventsByPlaceId(UUID placeId, LocalDateTime now);
}
