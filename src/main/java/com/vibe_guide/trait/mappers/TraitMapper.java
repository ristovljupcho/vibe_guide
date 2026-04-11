package com.vibe_guide.trait.mappers;

import com.vibe_guide.enums.TraitType;
import com.vibe_guide.trait.dtos.TraitCarouselResponseDTO;
import com.vibe_guide.trait.dtos.TraitResponseDTO;
import com.vibe_guide.trait.entities.Trait;
import com.vibe_guide.traitlike.entities.TraitLikesSummary;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class TraitMapper {
  public TraitResponseDTO toTraitResponseDTO(Trait trait) {
    UUID traitId = trait.getId();
    TraitType traitType = trait.getTraitType();
    String name = trait.getName();

    return new TraitResponseDTO(traitId, traitType, name);
  }

  public TraitResponseDTO toTraitResponseDTO(TraitLikesSummary traitLikesSummary) {
    UUID traitId = traitLikesSummary.getId();
    TraitType traitType = traitLikesSummary.getTraitType();
    String name = traitLikesSummary.getName();

    return new TraitResponseDTO(traitId, traitType, name);
  }

  public TraitCarouselResponseDTO toTraitCarouselResponseDTO(Trait trait) {
    String name = trait.getName();

    return new TraitCarouselResponseDTO(name);
  }
}
