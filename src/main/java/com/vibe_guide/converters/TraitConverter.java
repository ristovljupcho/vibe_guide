package com.vibe_guide.converters;

import com.vibe_guide.dtos.TraitCarouselResponseDTO;
import com.vibe_guide.dtos.TraitResponseDTO;
import com.vibe_guide.entities.Trait;
import com.vibe_guide.entities.views.TraitLikesSummary;
import com.vibe_guide.enums.TraitType;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * <ul>
 *     <li>This class is responsible for providing conversion methods from {@link Trait} entity to Data Transfer
 *     Objects and vice versa.</li>
 * </ul>
 */
@Component
public class TraitConverter {

    /**
     * Converts a {@link Trait} entity to a {@link TraitResponseDTO} entity.
     *
     * @param trait {@link Trait} entity to convert.
     * @return {@link TraitResponseDTO} converted entity.
     */
    public TraitResponseDTO toTraitResponseDTO(Trait trait) {
        UUID traitId = trait.getId();
        TraitType traitType = trait.getTraitType();
        String name = trait.getName();

        return new TraitResponseDTO(traitId, traitType, name);
    }

    /**
     * Converts a {@link TraitLikesSummary} entity to a {@link TraitResponseDTO} entity.
     *
     * @param traitLikesSummary {@link TraitLikesSummary} entity to convert.
     * @return {@link TraitResponseDTO} converted entity.
     */
    public TraitResponseDTO toTraitResponseDTO(TraitLikesSummary traitLikesSummary) {
        UUID traitId = traitLikesSummary.getId();
        TraitType traitType = traitLikesSummary.getTraitType();
        String name = traitLikesSummary.getName();

        return new TraitResponseDTO(traitId, traitType, name);
    }

    /**
     * Converts a {@link Trait} entity to a {@link TraitCarouselResponseDTO} entity.
     *
     * @param trait {@link Trait} entity to convert.
     * @return {@link TraitCarouselResponseDTO} converted entity.
     */
    public TraitCarouselResponseDTO toTraitCarouselResponseDTO(Trait trait) {
        String name = trait.getName();

        return new TraitCarouselResponseDTO(name);
    }
}