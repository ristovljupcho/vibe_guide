package com.vibe_guide.services.impl;

import com.vibe_guide.converters.PlaceConverter;
import com.vibe_guide.dtos.PlacePreviewResponseDTO;
import com.vibe_guide.dtos.PlaceResponseDTO;
import com.vibe_guide.entities.Place;
import com.vibe_guide.entities.Trait;
import com.vibe_guide.entities.views.PlaceTopTraits;
import com.vibe_guide.enums.sorting.PlaceSortBy;
import com.vibe_guide.enums.sorting.SortDirection;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.repositories.PlaceRepository;
import com.vibe_guide.repositories.PlaceTopTraitsRepository;
import com.vibe_guide.services.PlaceConverterWithAttributes;
import com.vibe_guide.services.PlaceQueryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PlaceQueryServiceImpl implements PlaceQueryService {

    private final PlaceRepository placeRepository;
    private final PlaceTopTraitsRepository placeTopTraitsRepository;
    private final PlaceConverterWithAttributes placeConverterWithAttributes;
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

        return placeConverterWithAttributes.getPlaceResponseDTO(place);
    }

    /**
     * Retrieves Page of {@link Place}s based on selected {@link Trait}s.
     *
     * @param traits        List of {@link String} representing names of each {@link Trait} selected. Used for
     *                      filtering {@link Place} based on selected {@link Trait}s.
     * @param sortBy        Used for sorting, values retrieved from enum {@link PlaceSortBy}.
     * @param sortDirection Used for sorting direction, values retrieved from enum {@link SortDirection}.
     * @param page          Page number.
     * @param size          Size of the page to be returned.
     * @return Page of {@link PlacePreviewResponseDTO}.
     */
    @Override
    public Page<PlacePreviewResponseDTO> getPaginatedPlaces(List<String> traits, PlaceSortBy sortBy,
                                                            SortDirection sortDirection, int page, int size) {
        PlaceSortBy actualSortBy = (sortBy != null) ? sortBy : PlaceSortBy.DEFAULT;

        String sortField = switch (actualSortBy) {
            case DEFAULT -> "name";
            case RATING -> "rating";
            case PRICE_LEVEL -> "priceLevel";
        };

        Sort sort = Sort.by(sortDirection == SortDirection.DESC ?
                Sort.Order.desc(sortField) :
                Sort.Order.asc(sortField));

        Pageable pageRequest = PageRequest.of(page, size, sort);

        Page<PlaceTopTraits> placePage;
        if (traits == null || traits.isEmpty()) {
            placePage = placeTopTraitsRepository.findAll(pageRequest);
        } else {
            int traitsSize = traits.size();
            placePage = placeTopTraitsRepository.findAllByTraitsPaginated(traits, traitsSize, pageRequest);
        }

        return placePage.map(placeConverter::toPlacePreviewResponseDTO);
    }

    @Override
    public List<PlacePreviewResponseDTO> getTopPlaces() {
        List<PlaceTopTraits> places = placeTopTraitsRepository.findTop10ByOrderByRatingDesc();

        return places.stream().map(placeConverter::toPlacePreviewResponseDTO).toList();
    }
}