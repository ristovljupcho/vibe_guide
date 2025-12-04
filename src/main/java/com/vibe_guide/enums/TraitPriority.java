package com.vibe_guide.enums;

import com.vibe_guide.entities.PlaceTrait;
import lombok.Getter;

/**
 * Represents the priority level assigned to a {@link PlaceTrait}.
 *
 * <p>
 * Priority values influence sorting when displaying traits for a place.
 * Each level is mapped to a numeric {@code weight}, where higher values
 * represent higher importance. These weights can also be used directly
 * in SQL ranking logic (e.g., ORDER BY weight DESC) without adding
 * additional persistence fields.
 * </p>
 *
 * <p>Priority levels:</p>
 * <ul>
 *     <li>{@code PAID_PROMOTION} — Highest priority, typically promoted content.</li>
 *     <li>{@code FAVOURITE} — Highlighted or editorially selected traits.</li>
 *     <li>{@code TRENDING} — Currently popular or high-engagement traits.</li>
 *     <li>{@code DEFAULT} — Standard neutral priority.</li>
 *     <li>{@code LOW} — Minimal importance, shown last.</li>
 * </ul>
 *
 * <p>⚠<b>IMPORTANT:</b> If priority names are changed, added, or removed, the
 * Liquibase initial schema (enum column default values and seed data)
 * must be updated accordingly.</p>
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
