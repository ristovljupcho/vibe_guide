package com.vibe_guide.traitlike.utils;

import lombok.experimental.UtilityClass;

/**
 * Utility class that holds constant response messages for Trait Like operations.
 * <p>
 * Centralizing messages here ensures consistency across services
 * and makes future updates easier.
 */
@UtilityClass
public class TraitLikeResponseMessages {
    public static final String TRAIT_LIKE_SUCCESSFUL_INSERT = "Traits successfully liked.";
    public static final String TRAIT_LIKE_SUCCESSFUL_DELETE = "Traits successfully unliked.";
}
