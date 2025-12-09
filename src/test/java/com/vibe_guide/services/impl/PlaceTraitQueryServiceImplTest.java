package com.vibe_guide.services.impl;

import com.vibe_guide.converters.TraitConverter;
import com.vibe_guide.data.TestDataFactory;
import com.vibe_guide.dtos.TraitCarouselResponseDTO;
import com.vibe_guide.dtos.TraitResponseDTO;
import com.vibe_guide.entities.Trait;
import com.vibe_guide.entities.views.TraitLikesSummary;
import com.vibe_guide.enums.TraitType;
import com.vibe_guide.repositories.PlaceRepository;
import com.vibe_guide.repositories.PlaceTraitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlaceTraitQueryServiceImplTest {

    @Mock
    private PlaceTraitRepository placeTraitRepository;

    @Mock
    private TraitConverter traitConverter;

    @Mock
    private PlaceRepository placeRepository;

    @InjectMocks
    private PlaceTraitQueryServiceImpl service;

    @Test
    void getTraitsForDisplayInPlaceCarouselMapsTraits() {
        var placeId = TestDataFactory.uuid();
        Trait trait = TestDataFactory.trait(TestDataFactory.uuid());
        TraitCarouselResponseDTO dto = TestDataFactory.traitCarouselResponseDto(trait.getName());
        when(placeRepository.existsById(placeId)).thenReturn(true);
        when(placeTraitRepository.getTraitsForPlaceCarousel(placeId)).thenReturn(List.of(trait));
        when(traitConverter.toTraitCarouselResponseDTO(trait)).thenReturn(dto);

        List<TraitCarouselResponseDTO> result = service.getTraitsForDisplayInPlaceCarousel(placeId);

        assertEquals(List.of(dto), result);
        verify(placeRepository).existsById(placeId);
    }

    @Test
    void getMissingTraitsForPlaceMapsEntities() {
        var placeId = TestDataFactory.uuid();
        Trait trait = TestDataFactory.trait(TestDataFactory.uuid());
        TraitResponseDTO dto = TestDataFactory.traitResponseDto(trait.getId());
        when(placeRepository.existsById(placeId)).thenReturn(true);
        when(placeTraitRepository.getMissingTraitsForPlace(placeId)).thenReturn(List.of(trait));
        when(traitConverter.toTraitResponseDTO(trait)).thenReturn(dto);

        List<TraitResponseDTO> result = service.getMissingTraitsForPlace(placeId);

        assertEquals(List.of(dto), result);
    }

    @Test
    void getMostPopularTraitsLimitsResult() {
        TraitLikesSummary summary = new TraitLikesSummary();
        summary.setId(TestDataFactory.uuid());
        summary.setTraitType(TraitType.FOOD);
        summary.setName("Signature dishes");
        TraitResponseDTO dto = TestDataFactory.traitResponseDto(summary.getId());
        when(placeTraitRepository.getTopTraits()).thenReturn(List.of(summary));
        when(traitConverter.toTraitResponseDTO(summary)).thenReturn(dto);

        List<TraitResponseDTO> result = service.getMostPopularTraits();

        assertEquals(List.of(dto), result);
        verify(placeTraitRepository).getTopTraits();
    }
}
