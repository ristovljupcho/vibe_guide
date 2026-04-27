package com.vibe_guide.specifications;

import com.vibe_guide.domain.event.entities.Event;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;

public class EventSpecification {

  /**
   * Returns a Specification that filters events by the given place ID.
   *
   * <p>If the provided placeId is null, this specification returns a conjunction, which effectively
   * means "no filtering" on placeId.
   *
   * @param placeId the UUID of the place to filter by; if placeId is null, no filtering is applied.
   * @return a Specification for filtering by placeId or no specification if placeId is null.
   */
  public static Specification<Event> hasPlaceId(UUID placeId) {
    return (Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
      if (placeId == null) {
        return cb.conjunction();
      }
      return cb.equal(root.get("place").get("id"), placeId);
    };
  }

  /**
   * Returns a Specification that filters events starting on or after the specified date.
   *
   * <p>If the provided startDate is null, it defaults to the current date and time.
   *
   * @param startDate the start date to filter by; if is null, defaults to LocalDateTime.now().
   * @return a Specification for filtering events that start on or after the effective start date.
   */
  public static Specification<Event> startsOnOrAfter(LocalDateTime startDate) {
    return (Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
      LocalDateTime effectiveStartDate = (startDate != null) ? startDate : LocalDateTime.now();
      return cb.greaterThanOrEqualTo(root.get("startDate"), effectiveStartDate);
    };
  }

  /**
   * Returns a Specification that filters events ending on or before the specified date.
   *
   * <p>If the provided endDate is null, this specification returns a conjunction, meaning no
   * filtering is applied for the event's end date.
   *
   * @param endDate the end date to filter by; if {@code null}, no filtering is applied.
   * @return a Specification for filtering events that end on or before the provided date, or a
   *     no-op specification if endDate is null.
   */
  public static Specification<Event> endsOnOrBefore(LocalDateTime endDate) {
    return (Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
      if (endDate == null) {
        return cb.conjunction();
      }
      return cb.lessThanOrEqualTo(root.get("endDate"), endDate);
    };
  }
}

