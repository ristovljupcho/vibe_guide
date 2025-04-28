package com.vibe_guide.services.impl;

import com.vibe_guide.dtos.DailyOfferInsertDTO;
import com.vibe_guide.dtos.DailyOfferUpdateDTO;
import com.vibe_guide.exceptions.DailyOfferNotFoundException;
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

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DailyOfferManagementServiceImplTest {
    @Mock
    DailyOfferRepository dailyOfferRepository;

    @Mock
    private PlaceRepository placeRepository;

    @InjectMocks
    DailyOfferManagementServiceImpl dailyOfferManagementService;

    @SneakyThrows
    @Test
    void insertDailyOffer_placeNotFound_throwsPlaceNotFoundException() {
        // given
        UUID placeId = PlaceTestData.PLACE_ID;
        DailyOfferInsertDTO dto = DailyOfferTestData.getDailyOfferInsertDTO();
        given(placeRepository.findById(any())).willReturn(Optional.empty());

        // when & then
        assertThatExceptionOfType(PlaceNotFoundException.class)
                .isThrownBy(() -> dailyOfferManagementService.insertDailyOffer(placeId, dto))
                .withMessageContaining("Place with id " + PlaceTestData.PLACE_ID + " not found");
        verify(dailyOfferRepository, times(0)).findAllById(any());
    }

    @Test
    void insertDailyOffer_dailyOfferInsertedSuccessfully_returnsMessage() {
        // given
        UUID placeId = PlaceTestData.PLACE_ID;
        DailyOfferInsertDTO dto = DailyOfferTestData.getDailyOfferInsertDTO();
        given(placeRepository.findById(any())).willReturn(Optional.of(PlaceTestData.getPlace()));

        // when
        String actualResult = dailyOfferManagementService.insertDailyOffer(placeId, dto);

        // then
        assertThat(actualResult).isEqualTo("DailyOffer successfully inserted.");
        verify(dailyOfferRepository, times(1)).save(any());
    }

    @SneakyThrows
    @Test
    void updateDailyOffer_placeNotFound_throwsPlaceNotFoundException() {
        // given
        UUID placeId = PlaceTestData.PLACE_ID;
        DailyOfferUpdateDTO dto = DailyOfferTestData.getDailyOfferUpdateDTO();
        given(placeRepository.findById(any())).willReturn(Optional.empty());

        // when & then
        assertThatExceptionOfType(PlaceNotFoundException.class)
                .isThrownBy(
                        () -> dailyOfferManagementService.updateDailyOffer(placeId, DailyOfferTestData.DAILY_OFFER_ID,
                                dto))
                .withMessage("Place with id " + placeId + " not found.");
        verify(dailyOfferRepository, times(0)).save(any());
    }

    @Test
    void updateDailyOffer_dailyOfferUpdateSuccessfully_returnsMessage() {
        // given
        UUID placeId = PlaceTestData.PLACE_ID;
        DailyOfferUpdateDTO dto = DailyOfferTestData.getDailyOfferUpdateDTO();
        given(placeRepository.findById(any())).willReturn(Optional.of(PlaceTestData.getPlace()));
        given(dailyOfferRepository.findById(any())).willReturn(Optional.of(DailyOfferTestData.getDailyOffer()));

        // when
        String actualResult =
                dailyOfferManagementService.updateDailyOffer(placeId, DailyOfferTestData.DAILY_OFFER_ID, dto);

        // then
        assertThat(actualResult).isEqualTo("DailyOffer successfully updated.");
    }

    @SneakyThrows
    @Test
    void deleteDailyOffer_placeNotFound_throwsPlaceNotFoundException() {
        // given
        UUID dailyOfferId = DailyOfferTestData.DAILY_OFFER_ID;
        given(dailyOfferRepository.findById(any())).willReturn(Optional.empty());

        // when & then
        assertThatExceptionOfType(DailyOfferNotFoundException.class)
                .isThrownBy(() -> dailyOfferManagementService.deleteDailyOffer(dailyOfferId))
                .withMessage("Daily Offer with id '" + dailyOfferId + "' not found.");
        verify(dailyOfferRepository, times(0)).delete(any());
    }

    @Test
    void deleteDailyOffer_dailyOfferDeletionSuccessfully_returnsMessage() {
        // given
        UUID dailyOfferId = DailyOfferTestData.DAILY_OFFER_ID;
        given(dailyOfferRepository.findById(any())).willReturn(Optional.of(DailyOfferTestData.getDailyOffer()));

        // when
        String actualResult = dailyOfferManagementService.deleteDailyOffer(dailyOfferId);

        // then
        assertThat(actualResult).isEqualTo("DailyOffer successfully deleted.");
    }
}