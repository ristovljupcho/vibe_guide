package com.vibe_guide.services.impl;

import com.vibe_guide.converters.PlaceConverter;
import com.vibe_guide.data.TestDataFactory;
import com.vibe_guide.dtos.PlacePreviewResponseDTO;
import com.vibe_guide.dtos.PlaceResponseDTO;
import com.vibe_guide.entities.Place;
import com.vibe_guide.entities.views.PlaceTopTraits;
import com.vibe_guide.enums.PrimaryType;
import com.vibe_guide.enums.PriceLevel;
import com.vibe_guide.enums.sorting.PlaceSortBy;
import com.vibe_guide.enums.sorting.SortDirection;
import com.vibe_guide.repositories.PlaceRepository;
import com.vibe_guide.repositories.PlaceTopTraitsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlaceQueryServiceImplTest {

    @Mock
    private PlaceRepository placeRepository;

    @Mock
    private PlaceTopTraitsRepository placeTopTraitsRepository;

    @Mock
    private PlaceConverter placeConverter;

    @InjectMocks
    private PlaceQueryServiceImpl service;

    @Test
    void getPlaceByIdReturnsDto() {
        UUID placeId = TestDataFactory.uuid();
        Place place = TestDataFactory.place(placeId);
        PlaceResponseDTO dto = TestDataFactory.placeResponseDto();
        when(placeRepository.findById(placeId)).thenReturn(Optional.of(place));
        when(placeConverter.toPlaceResponseDTO(place)).thenReturn(dto);

        PlaceResponseDTO result = service.getPlaceById(placeId);

        assertEquals(dto, result);
    }

    @Test
    void getPlacesSortsResultsBasedOnDirection() {
        PlaceTopTraits alpha = mock(PlaceTopTraits.class);
        when(alpha.getName()).thenReturn("Alpha");
        when(alpha.getRating()).thenReturn(4.0);
        when(alpha.getPriceLevel()).thenReturn(PriceLevel.MODERATE);
        PlaceTopTraits beta = mock(PlaceTopTraits.class);
        when(beta.getName()).thenReturn("Beta");
        when(beta.getRating()).thenReturn(4.5);
        when(beta.getPriceLevel()).thenReturn(PriceLevel.MODERATE);
        when(placeTopTraitsRepository.findAll()).thenReturn(List.of(alpha, beta));
        PlacePreviewResponseDTO alphaDto = new PlacePreviewResponseDTO(TestDataFactory.uuid(), "Alpha", "", 4.0,
                PrimaryType.CAFE, PriceLevel.MODERATE, new String[0]);
        PlacePreviewResponseDTO betaDto = new PlacePreviewResponseDTO(TestDataFactory.uuid(), "Beta", "", 4.5,
                PrimaryType.CAFE, PriceLevel.MODERATE, new String[0]);
        when(placeConverter.toPlacePreviewResponseDTO(alpha)).thenReturn(alphaDto);
        when(placeConverter.toPlacePreviewResponseDTO(beta)).thenReturn(betaDto);

        List<PlacePreviewResponseDTO> result = service.getPlaces(null, PlaceSortBy.DEFAULT, SortDirection.ASC);

        assertEquals(List.of(betaDto, alphaDto), result);
        InOrder order = inOrder(placeConverter);
        order.verify(placeConverter).toPlacePreviewResponseDTO(beta);
        order.verify(placeConverter).toPlacePreviewResponseDTO(alpha);
    }

    @Test
    void getTopPlacesReturnsMappedDtos() {
        PlaceTopTraits topTrait = mock(PlaceTopTraits.class);
        PlacePreviewResponseDTO dto = TestDataFactory.placePreviewResponseDto(TestDataFactory.uuid());
        when(placeTopTraitsRepository.findTop10ByOrderByRatingDesc()).thenReturn(List.of(topTrait));
        when(placeConverter.toPlacePreviewResponseDTO(topTrait)).thenReturn(dto);

        List<PlacePreviewResponseDTO> result = service.getTopPlaces();

        assertEquals(List.of(dto), result);
    }
}
