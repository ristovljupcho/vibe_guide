package com.vibe_guide.domain.placetrait.mappers;

import com.vibe_guide.domain.place.dtos.PlaceCreateTraitDTO;
import com.vibe_guide.domain.placetrait.dtos.PlaceTraitRequestDTO;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlaceTraitMapper {
  @Mapping(target = "placeId", source = "placeId")
  @Mapping(target = "traitId", source = "trait.traitId")
  @Mapping(target = "additionalInformation", source = "trait.additionalInformation")
  @Mapping(target = "priority", source = "trait.priority")
  PlaceTraitRequestDTO toPlaceTraitRequestDTO(UUID placeId, PlaceCreateTraitDTO trait);
}

