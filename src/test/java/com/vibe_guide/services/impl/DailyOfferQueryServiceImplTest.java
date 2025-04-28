package com.vibe_guide.services.impl;

import com.vibe_guide.converters.DailyOfferConverter;
import com.vibe_guide.dtos.DailyOfferResponseDTO;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.repositories.DailyOfferRepository;
import com.vibe_guide.repositories.PlaceRepository;
import com.vibe_guide.utils.DailyOfferTestData;
import com.vibe_guide.utils.PlaceTestData;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class DailyOfferQueryServiceImplTest {
    @Mock
    DailyOfferRepository dailyOfferRepository;

    @Mock
    DailyOfferConverter dailyOfferConverter;

    @Mock
    private PlaceRepository placeRepository;

    @InjectMocks
    DailyOfferQueryServiceImpl dailyOfferQueryService;

    @SneakyThrows
    @Test
    void getTodayDailyOffersByPlaceId_placeNotFound_throwsPlaceNotFoundException() {
        // given
        given(placeRepository.findById(any())).willReturn(Optional.empty());

        // when & then
        assertThatExceptionOfType(PlaceNotFoundException.class)
                .isThrownBy(() -> dailyOfferQueryService.getTodayDailyOffersByPlaceId(PlaceTestData.PLACE_ID))
                .withMessage("Place with id " + PlaceTestData.PLACE_ID + " not found.");
        verify(dailyOfferRepository, times(0)).findTodayDailyOffersByPlaceId(any(), any());
        verify(dailyOfferConverter, times(0)).toDailyOfferResponseDTO(any());
    }

    @Test
    void getTodayDailyOffersByPlaceId_placeFoundWithProvidedId_returnsDailyOfferResponseDTO() {
        // given
        List<DailyOfferResponseDTO> dtos = DailyOfferTestData.getDailyOfferResponseDTOs();
        given(placeRepository.findById(any())).willReturn(Optional.of(PlaceTestData.getPlace()));
        given(dailyOfferRepository.findTodayDailyOffersByPlaceId(any(), any())).willReturn(
                DailyOfferTestData.getDailyOffers());
        given(dailyOfferConverter.toDailyOfferResponseDTO(any())).willReturn(dtos.get(0), dtos.get(1), dtos.get(2));

        // when
        List<DailyOfferResponseDTO> actualResult =
                dailyOfferQueryService.getTodayDailyOffersByPlaceId(DailyOfferTestData.DAILY_OFFER_ID);

        // then
        assertThat(actualResult).isEqualTo(dtos);
    }

    @Test
    void getTodayDailyOffers_successfulFetch_returnsDailyOfferResponseDTO() {
        // given
        List<DailyOfferResponseDTO> dtos = DailyOfferTestData.getDailyOfferResponseDTOs();
        given(dailyOfferRepository.findTodayDailyOffers(any())).willReturn(DailyOfferTestData.getDailyOffers());
        given(dailyOfferConverter.toDailyOfferResponseDTO(any())).willReturn(dtos.get(0), dtos.get(1), dtos.get(2));

        // when
        List<DailyOfferResponseDTO> actualResult = dailyOfferQueryService.getTodayDailyOffers();

        // then
        assertThat(actualResult).isEqualTo(dtos);
    }
}