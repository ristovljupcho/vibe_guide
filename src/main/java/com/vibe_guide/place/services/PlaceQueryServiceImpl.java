package com.vibe_guide.place.services;

import com.vibe_guide.enums.sorting.PlaceSortBy;
import com.vibe_guide.enums.sorting.SortDirection;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.place.dtos.PlacePreviewResponseDTO;
import com.vibe_guide.place.dtos.PlaceResponseDTO;
import com.vibe_guide.place.entities.Place;
import com.vibe_guide.place.entities.PlaceTopTraits;
import com.vibe_guide.place.mappers.PlaceMapper;
import com.vibe_guide.place.repositories.PlaceRepository;
import com.vibe_guide.place.repositories.PlaceTopTraitsRepository;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlaceQueryServiceImpl implements PlaceQueryService {

  private final PlaceRepository placeRepository;
  private final PlaceTopTraitsRepository placeTopTraitsRepository;
  private final PlaceMapper placeMapper;

  /**
   * Retrieves {@link Place} with provided ID.
   *
   * @param placeId ID of the targeted {@link Place}.
   * @return DTO with type {@link PlaceResponseDTO}.
   */
  @Override
  public PlaceResponseDTO getPlaceById(UUID placeId) {
    Place place =
        placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));

    return placeMapper.toPlaceResponseDTO(place);
  }

  @Override
  public List<PlacePreviewResponseDTO> getPlaces(
      List<String> traits, PlaceSortBy sortBy, SortDirection sortDirection) {
    PlaceSortBy actualSortBy = (sortBy != null) ? sortBy : PlaceSortBy.DEFAULT;
    SortDirection actualSortDirection =
        (sortDirection != null) ? sortDirection : SortDirection.DESC;
    List<PlaceTopTraits> places = loadPlaces(traits);
    Comparator<PlaceTopTraits> comparator = buildComparator(actualSortBy, actualSortDirection);

    places.sort(comparator);

    return places.stream().map(placeMapper::toPlacePreviewResponseDTO).toList();
  }

  @Override
  public List<PlacePreviewResponseDTO> getTopPlaces() {
    List<PlaceTopTraits> places = placeTopTraitsRepository.findTop10ByOrderByRatingDesc();

    return places.stream().map(placeMapper::toPlacePreviewResponseDTO).toList();
  }

  /**
   * Loads places from the aggregated view, optionally filtered by trait names.
   *
   * @param traits optional list of trait names used for filtering
   * @return matching place projections ready for sorting and mapping
   */
  private List<PlaceTopTraits> loadPlaces(List<String> traits) {
    if (traits == null || traits.isEmpty()) {
      return placeTopTraitsRepository.findAll();
    }

    return placeTopTraitsRepository.findAllByTraits(traits, traits.size());
  }

  /**
   * Builds a comparator for place preview sorting while keeping direction handling consistent.
   *
   * @param sortBy selected sort field
   * @param sortDirection selected sort direction
   * @return comparator configured for the requested sort
   */
  private Comparator<PlaceTopTraits> buildComparator(
      PlaceSortBy sortBy, SortDirection sortDirection) {
    Comparator<PlaceTopTraits> comparator =
        switch (sortBy) {
          case DEFAULT ->
              Comparator.comparing(PlaceTopTraits::getName, String.CASE_INSENSITIVE_ORDER);
          case RATING ->
              Comparator.comparing(
                  PlaceTopTraits::getRating, Comparator.nullsLast(Double::compareTo));
          case PRICE_LEVEL ->
              Comparator.comparing(
                  PlaceTopTraits::getPriceLevel, Comparator.nullsLast(Enum::compareTo));
        };

    return sortDirection == SortDirection.DESC ? comparator.reversed() : comparator;
  }
}
