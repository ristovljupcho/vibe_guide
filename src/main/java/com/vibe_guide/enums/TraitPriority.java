package com.vibe_guide.enums;

import lombok.Getter;

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
