package com.vibe_guide.converters;

import com.vibe_guide.dtos.TraitPreviewResponseDTO;
import com.vibe_guide.entities.Trait;
import com.vibe_guide.enums.TraitType;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * <ul>
 *     <li>Performs conversion of object from type {@link Trait} to {@link TraitPreviewResponseDTO}.</li>
 * </ul>
 */
@Component
public class TraitConverter {

    /**
     * Converts a {@link Trait} entity to a {@link TraitPreviewResponseDTO} entity.
     *
     * @param trait {@link Trait} entity to convert.
     * @return {@link TraitPreviewResponseDTO} converted entity.
     */
    public TraitPreviewResponseDTO toTraitPreviewResponseDTO(Trait trait) {
        UUID traitId = UUID.randomUUID();
        TraitType traitType = trait.getTraitType();
        String name = trait.getName();

        return new TraitPreviewResponseDTO(traitId, traitType, name);
    }
}