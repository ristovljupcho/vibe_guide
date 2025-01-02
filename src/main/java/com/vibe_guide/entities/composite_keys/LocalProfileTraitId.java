package com.vibe_guide.entities.composite_keys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocalProfileTraitId implements Serializable {
    private UUID trait;
    private UUID localProfile;
}