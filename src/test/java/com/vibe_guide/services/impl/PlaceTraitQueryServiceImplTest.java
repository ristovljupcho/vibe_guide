package com.vibe_guide.services.impl;

import com.vibe_guide.converters.TraitConverter;
import com.vibe_guide.dtos.TraitCarouselResponseDTO;
import com.vibe_guide.dtos.TraitResponseDTO;
import com.vibe_guide.entities.Trait;
import com.vibe_guide.entities.views.TraitLikesSummary;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.repositories.PlaceRepository;
import com.vibe_guide.repositories.PlaceTraitRepository;
import com.vibe_guide.utils.PlaceTestData;
import com.vibe_guide.utils.TraitTestData;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PlaceTraitQueryServiceImplTest {
    @Mock
    PlaceRepository placeRepository;

    @Mock
    PlaceTraitRepository placeTraitRepository;

    @Mock
    TraitConverter traitConverter;

    @InjectMocks
    PlaceTraitQueryServiceImpl placeTraitQueryService;

    @SneakyThrows
    @Test
    void getTraitsForPlaceCarousel_placeNotFound_throwsPlaceNotFoundException() {
        // given
        given(placeRepository.existsById(any())).willReturn(false);

        // when & then
        assertThatExceptionOfType(PlaceNotFoundException.class)
                .isThrownBy(() -> placeTraitQueryService.getTraitsForPlaceCarousel(PlaceTestData.PLACE_ID))
                .withMessage("Place with id " + PlaceTestData.PLACE_ID + " not found.");
        verify(placeTraitRepository, times(0)).getTraitsForPlaceCarousel(any());
        verify(traitConverter, times(0)).toTraitCarouselResponseDTO(any());
    }

    @Test
    void getTraitsForPlaceCarousel_atLeastOneTraitIsFound_returnListOfTraitCarouselResponseDTO() {
        // given
        List<TraitCarouselResponseDTO> DTOs = TraitTestData.getTraitCarouselResponseDTOs();

        given(placeRepository.existsById(any())).willReturn(true);
        given(placeTraitRepository.getTraitsForPlaceCarousel(any())).willReturn(TraitTestData.getTraits());
        given(traitConverter.toTraitCarouselResponseDTO(any())).willReturn(DTOs.get(0), DTOs.get(1), DTOs.get(2));

        // when
        List<TraitCarouselResponseDTO> actualResult =
                placeTraitQueryService.getTraitsForPlaceCarousel(PlaceTestData.PLACE_ID);

        // then
        assertThat(actualResult).isEqualTo(DTOs);
    }

    @SneakyThrows
    @Test
    void getTopTraitsForPlace_placeNotFound_throwsPlaceNotFoundException() {
        // given
        given(placeRepository.existsById(any())).willReturn(false);

        // when & then
        assertThatExceptionOfType(PlaceNotFoundException.class)
                .isThrownBy(() -> placeTraitQueryService.getTopTraitsForPlace(PlaceTestData.PLACE_ID))
                .withMessage("Place with id " + PlaceTestData.PLACE_ID + " not found.");
        verify(placeTraitRepository, times(0)).getTopTraitsForPlace(any());
        verify(traitConverter, times(0)).toTraitResponseDTO((Trait) any());
    }

    @Test
    void getTopTraitsForPlace_atLeastOneTraitIsFound_returnListOfTraitResponseDTO() {
        // given
        List<Trait> traits = TraitTestData.getTraits();
        List<TraitResponseDTO> DTOs = TraitTestData.getTraitResponseDTOs();

        given(placeRepository.existsById(any())).willReturn(true);
        given(placeTraitRepository.getTopTraitsForPlace(any())).willReturn(traits);
        given(traitConverter.toTraitResponseDTO((Trait) any())).willReturn(DTOs.get(0), DTOs.get(1), DTOs.get(2));

        // when
        List<TraitResponseDTO> actualResult = placeTraitQueryService.getTopTraitsForPlace(PlaceTestData.PLACE_ID);

        // then
        assertThat(actualResult).isEqualTo(DTOs);
    }

    @SneakyThrows
    @Test
    void getMissingTraitsForPlace_placeNotFound_throwsPlaceNotFoundException() {
        // given
        given(placeRepository.existsById(any())).willReturn(false);

        // then & when
        assertThatExceptionOfType(PlaceNotFoundException.class)
                .isThrownBy(() -> placeTraitQueryService.getMissingTraitsForPlace(PlaceTestData.PLACE_ID))
                .withMessage("Place with id " + PlaceTestData.PLACE_ID + " not found.");
        verify(placeTraitRepository, times(0)).getMissingTraitsForPlace(any());
        verify(traitConverter, times(0)).toTraitResponseDTO((Trait) any());
    }

    @Test
    void getMissingTraitsForPlace_atLeastOneTraitIsFound_returnListOfTraitResponseDTO() {
        // given
        List<TraitResponseDTO> DTOs = TraitTestData.getTraitResponseDTOs();

        given(placeRepository.existsById(any())).willReturn(true);
        given(placeTraitRepository.getMissingTraitsForPlace(any())).willReturn(TraitTestData.getTraits());
        given(traitConverter.toTraitResponseDTO((Trait) any())).willReturn(DTOs.get(0), DTOs.get(1), DTOs.get(2));

        // when
        List<TraitResponseDTO> actualResult = placeTraitQueryService.getMissingTraitsForPlace(PlaceTestData.PLACE_ID);

        // then
        assertThat(actualResult).isEqualTo(DTOs);
    }

    @Test
    void getMostPopularTraits_atLeastOneTraitIsFound_returnListOfTraitResponseDTO() {
        // given
        List<TraitResponseDTO> DTOs = TraitTestData.getTraitResponseDTOs();

        given(placeTraitRepository.getTopTraits()).willReturn(TraitTestData.getTraitLikeSummaries());
        given(traitConverter.toTraitResponseDTO((TraitLikesSummary) any())).willReturn(DTOs.get(0), DTOs.get(1),
                DTOs.get(2));

        // when
        List<TraitResponseDTO> actualResult = placeTraitQueryService.getMostPopularTraits();

        // then
        assertThat(actualResult).isEqualTo(DTOs);
    }
}