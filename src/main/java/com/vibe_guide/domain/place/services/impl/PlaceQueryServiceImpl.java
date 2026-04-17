package com.vibe_guide.domain.place.services.impl;

import com.vibe_guide.domain.place.services.PlaceQueryService;
import com.vibe_guide.enums.sorting.PlaceSortBy;
import com.vibe_guide.enums.sorting.SortDirection;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.domain.place.dtos.PlacePreviewResponseDTO;
import com.vibe_guide.domain.place.dtos.PlaceResponseDTO;
import com.vibe_guide.domain.place.entities.Place;
import com.vibe_guide.domain.place.entities.PlaceTopTraits;
import com.vibe_guide.domain.place.mappers.PlaceMapper;
import com.vibe_guide.domain.place.repositories.PlaceRepository;
import com.vibe_guide.domain.place.repositories.PlaceTopTraitsRepository;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlaceQueryServiceImpl implements PlaceQueryService {

  private final PlaceRepository placeRepository;
  private final PlaceTopTraitsRepository placeTopTraitsRepository;
  private final PlaceMapper placeMapper;

  @Override
  public PlaceResponseDTO getById(UUID placeId) {
    Place place =
        placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));

    return placeMapper.toPlaceResponseDTO(place);
  }

  @Override
  public List<PlacePreviewResponseDTO> getAll(
      List<String> traits, PlaceSortBy sortBy, SortDirection sortDirection) {
    PlaceSortBy actualSortBy = (sortBy != null) ? sortBy : PlaceSortBy.DEFAULT;
    SortDirection actualSortDirection =
        (sortDirection != null) ? sortDirection : SortDirection.DESC;
    Sort sort = buildSort(actualSortBy, actualSortDirection);
    List<PlaceTopTraits> places = loadPlaces(traits, sort);

    return places.stream().map(placeMapper::toPlacePreviewResponseDTO).toList();
  }

  @Override
  public List<PlacePreviewResponseDTO> getTop() {
    List<PlaceTopTraits> places = placeTopTraitsRepository.findTop10ByOrderByRatingDesc();

    return places.stream().map(placeMapper::toPlacePreviewResponseDTO).toList();
  }

  private List<PlaceTopTraits> loadPlaces(List<String> traits, Sort sort) {
    if (traits == null || traits.isEmpty()) {
      return placeTopTraitsRepository.findAll(sort);
    }

    return placeTopTraitsRepository.findAllByTraits(traits, traits.size(), sort);
  }

  private Sort buildSort(
      PlaceSortBy sortBy, SortDirection sortDirection) {
    String sortField =
        switch (sortBy) {
          case DEFAULT -> "name";
          case RATING -> "rating";
          case PRICE_LEVEL -> "priceLevel";
        };

    return Sort.by(
        sortDirection == SortDirection.DESC
            ? Sort.Order.desc(sortField)
            : Sort.Order.asc(sortField));
  }
}



