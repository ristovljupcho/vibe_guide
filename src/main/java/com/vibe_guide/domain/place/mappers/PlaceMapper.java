package com.vibe_guide.domain.place.mappers;

import com.vibe_guide.domain.place.dtos.PlaceCreateDTO;
import com.vibe_guide.domain.place.dtos.PlacePreviewResponseDTO;
import com.vibe_guide.domain.place.dtos.PlaceUpdateDTO;
import com.vibe_guide.domain.place.dtos.PlaceResponseDTO;
import com.vibe_guide.domain.place.entities.Place;
import com.vibe_guide.domain.place.entities.PlaceTopTraits;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PlaceMapper {
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "rating", ignore = true)
  @Mapping(target = "admins", ignore = true)
  @Mapping(target = "traits", ignore = true)
  @Mapping(target = "gallery", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "createdBy", ignore = true)
  @Mapping(target = "updatedBy", ignore = true)
  Place toPlace(PlaceCreateDTO dto);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "rating", ignore = true)
  @Mapping(target = "admins", ignore = true)
  @Mapping(target = "traits", ignore = true)
  @Mapping(target = "gallery", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "createdBy", ignore = true)
  @Mapping(target = "updatedBy", ignore = true)
  Place toPlace(PlaceUpdateDTO dto);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "rating", ignore = true)
  @Mapping(target = "admins", ignore = true)
  @Mapping(target = "traits", ignore = true)
  @Mapping(target = "gallery", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "createdBy", ignore = true)
  @Mapping(target = "updatedBy", ignore = true)
  void applyRequest(PlaceUpdateDTO dto, @MappingTarget Place place);

  @Mapping(target = "imageUrls", expression = "java(toImageUrls(place))")
  PlaceResponseDTO toPlaceResponseDTO(Place place);

  PlacePreviewResponseDTO toPlacePreviewResponseDTO(PlaceTopTraits placeTopTraits);

  default List<String> toImageUrls(Place place) {
    return place.getGallery().stream().map(gallery -> gallery.getPhoto()).toList();
  }
}



