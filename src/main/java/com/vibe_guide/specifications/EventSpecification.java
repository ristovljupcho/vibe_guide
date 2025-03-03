package com.vibe_guide.specifications;

import com.vibe_guide.entities.Event;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class EventSpecification {

    public static Specification<Event> hasPlaceId(UUID placeId) {
        return (Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb.equal(root.get("place").get("id"), placeId);
    }

    public static Specification<Event> hasPlaceName(String placeName) {
        return (Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb.equal(cb.lower(root.get("place").get("name")), placeName.toLowerCase() );
    }

    public static Specification<Event> containsEventName(String eventName) {
        return (Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb.like(cb.lower(root.get("name")), "%" + eventName.toLowerCase() + "%");
    }

    public static Specification<Event> startDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return (Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
                cb.between(root.get("startDate"), startDate, endDate);
    }

    public static Specification<Event> isToday() {
        return (Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Expression<LocalDate> startDateAsDate = cb.function("DATE", LocalDate.class, root.get("startDate"));
            return cb.equal(startDateAsDate, LocalDate.now());
        };
    }
}