package com.vibe_guide.enums;

import com.vibe_guide.placetrait.entities.PlaceTrait;
import lombok.Getter;

/**
 * Represents the priority level assigned to a {@link PlaceTrait}.
 *
 * <p>Priority values influence sorting when displaying traits for a place. Each level is mapped to
 * a numeric {@code weight}, where higher values represent higher importance. These weights can also
 * be used directly in SQL ranking logic (e.g., ORDER BY weight DESC) without adding additional
 * persistence fields.
 *
 * <p>Priority levels:
 *
 * <ul>
 *   <li>{@code PAID_PROMOTION} Ã¢â‚¬â€ Highest priority, typically promoted content.
 *   <li>{@code FAVOURITE} Ã¢â‚¬â€ Highlighted or editorially selected traits.
 *   <li>{@code TRENDING} Ã¢â‚¬â€ Currently popular or high-engagement traits.
 *   <li>{@code DEFAULT} Ã¢â‚¬â€ Standard neutral priority.
 *   <li>{@code LOW} Ã¢â‚¬â€ Minimal importance, shown last.
 * </ul>
 *
 * <p>Ã¢Å¡Â <b>IMPORTANT:</b> If priority names are changed, added, or removed, the Liquibase
 * initial schema (enum column default values and seed data) must be updated accordingly.
 */
@Getter
public enum TraitPriority {
  PAID_PROMOTION(100),
  FAVOURITE(80),
  TRENDING(60),
  DEFAULT(20),
  LOW(0);

  private final int weight;

  TraitPriority(int weight) {
    this.weight = weight;
  }
}
