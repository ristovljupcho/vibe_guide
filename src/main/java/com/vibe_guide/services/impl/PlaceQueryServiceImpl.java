package com.vibe_guide.services.impl;

import com.vibe_guide.converters.PlaceConverter;
import com.vibe_guide.dtos.PlacePreviewResponseDTO;
import com.vibe_guide.dtos.PlaceResponseDTO;
import com.vibe_guide.entities.Place;
import com.vibe_guide.entities.views.PlaceTopTraits;
import com.vibe_guide.enums.sorting.PlaceSortBy;
import com.vibe_guide.enums.sorting.SortDirection;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.repositories.PlaceRepository;
import com.vibe_guide.repositories.PlaceTopTraitsRepository;
import com.vibe_guide.services.PlaceQueryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PlaceQueryServiceImpl implements PlaceQueryService {

    private final PlaceRepository placeRepository;
    private final PlaceTopTraitsRepository placeTopTraitsRepository;
    private final PlaceConverter placeConverter;

    /**
     * Retrieves {@link Place} with provided ID.
     *
     * @param placeId ID of the targeted {@link Place}.
     * @return DTO with type {@link PlaceResponseDTO}.
     */
    @Override
    public PlaceResponseDTO getPlaceById(UUID placeId) {
        Place place = placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));

        return placeConverter.toPlaceResponseDTO(place);
    }

    @Override
    public List<PlacePreviewResponseDTO> getPlaces(List<String> traits, PlaceSortBy sortBy, SortDirection sortDirection) {
        PlaceSortBy actualSortBy = (sortBy != null) ? sortBy : PlaceSortBy.DEFAULT;
        SortDirection actualSortDirection = (sortDirection != null) ? sortDirection : SortDirection.DESC;

        List<PlaceTopTraits> places;
        if (traits == null || traits.isEmpty()) {
            places = placeTopTraitsRepository.findAll();
        } else {
            int traitsSize = traits.size();
            places = placeTopTraitsRepository.findAllByTraits(traits, traitsSize);
        }

        Comparator<PlaceTopTraits> comparator = switch (actualSortBy) {
            case DEFAULT -> Comparator.comparing(PlaceTopTraits::getName, String.CASE_INSENSITIVE_ORDER);
            case RATING -> Comparator.comparing(PlaceTopTraits::getRating, Comparator.nullsLast(Double::compareTo));
            case PRICE_LEVEL -> Comparator.comparing(PlaceTopTraits::getPriceLevel, Comparator.nullsLast(Enum::compareTo));
        };

        if (actualSortDirection == SortDirection.ASC) {
            comparator = comparator.reversed();
        }

        places.sort(comparator);

        return places.stream()
                .map(placeConverter::toPlacePreviewResponseDTO)
                .toList();
    }


    @Override
    public List<PlacePreviewResponseDTO> getTopPlaces() {
        List<PlaceTopTraits> places = placeTopTraitsRepository.findTop10ByOrderByRatingDesc();

        return places.stream().map(placeConverter::toPlacePreviewResponseDTO).toList();
    }
}