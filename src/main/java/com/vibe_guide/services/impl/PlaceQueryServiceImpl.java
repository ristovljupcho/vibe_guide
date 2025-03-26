package com.vibe_guide.services.impl;

import com.vibe_guide.converters.PlaceConverter;
import com.vibe_guide.dtos.EventResponseDTO;
import com.vibe_guide.dtos.PlacePreviewResponseDTO;
import com.vibe_guide.dtos.PlaceResponseDTO;
import com.vibe_guide.dtos.TraitCarouselResponseDTO;
import com.vibe_guide.dtos.TraitResponseDTO;
import com.vibe_guide.entities.Place;
import com.vibe_guide.entities.views.PlaceTopTraits;
import com.vibe_guide.enums.sorting.PlaceSortBy;
import com.vibe_guide.enums.sorting.SortDirection;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.repositories.PlaceRepository;
import com.vibe_guide.repositories.PlaceTopTraitsRepository;
import com.vibe_guide.services.PlaceQueryService;
import com.vibe_guide.services.PlaceTraitQueryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PlaceQueryServiceImpl implements PlaceQueryService {

    private final PlaceRepository placeRepository;
    private final PlaceTopTraitsRepository placeTopTraitsRepository;
    private final PlaceTraitQueryService placeTraitQueryService;
    private final PlaceConverter placeConverter;

    @Override
    public PlaceResponseDTO getPlaceById(UUID placeId) {
        Place place = placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));

        List<TraitResponseDTO> topTraits = placeTraitQueryService.getTopTraitsForPlace(placeId);
        List<EventResponseDTO> dailyEvents = new ArrayList<>();
        List<EventResponseDTO> monthlyEvents = new ArrayList<>();
        List<TraitCarouselResponseDTO> carouselTraits = placeTraitQueryService.getTraitsForPlaceCarousel(placeId);

        return placeConverter.toPlaceResponseDTO(place, topTraits, dailyEvents, monthlyEvents, carouselTraits);
    }

    @Override
    public Page<PlacePreviewResponseDTO> getPaginatedPlaces(List<String> traits, PlaceSortBy sortBy,
                                                            SortDirection sortOrder, int page, int size) {
        String sortField = switch (sortBy) {
            case DEFAULT -> "name";
            case RATING -> "rating";
            case PRICE_LEVEL -> "priceLevel";
        };

        Sort sort = Sort.by(sortOrder == SortDirection.DESC ?
                Sort.Order.desc(sortField) :
                Sort.Order.asc(sortField));

        Pageable pageRequest = PageRequest.of(page, size, sort);

        Page<PlaceTopTraits> placePage;
        if (traits.isEmpty()) {
            placePage = placeTopTraitsRepository.findAllPaginated(pageRequest);
        } else {
            int traitsSize = traits.size();
            placePage = placeTopTraitsRepository.findAllByTraitsPaginated(traits, traitsSize, pageRequest);
        }

        return placePage.map(placeConverter::toPlacePreviewResponseDTO);
    }
}