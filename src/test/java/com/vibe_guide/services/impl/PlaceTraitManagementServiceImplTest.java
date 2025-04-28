package com.vibe_guide.services.impl;

import com.vibe_guide.dtos.BatchDeleteTraitsInPlace;
import com.vibe_guide.dtos.BatchInsertTraitsInPlace;
import com.vibe_guide.dtos.PlaceTraitRequestDTO;
import com.vibe_guide.entities.PlaceTrait;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.exceptions.TraitAlreadyPresentForPlaceException;
import com.vibe_guide.exceptions.TraitForPlaceNotFound;
import com.vibe_guide.exceptions.TraitNotFoundException;
import com.vibe_guide.exceptions.TraitsAlreadyPresentForPlaceException;
import com.vibe_guide.exceptions.TraitsNotFoundException;
import com.vibe_guide.repositories.PlaceRepository;
import com.vibe_guide.repositories.PlaceTraitRepository;
import com.vibe_guide.repositories.TraitRepository;
import com.vibe_guide.utils.PlaceTestData;
import com.vibe_guide.utils.PlaceTraitTestData;
import com.vibe_guide.utils.TraitTestData;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PlaceTraitManagementServiceImplTest {
    @Mock
    private PlaceRepository placeRepository;

    @Mock
    private TraitRepository traitRepository;

    @Mock
    private PlaceTraitRepository placeTraitRepository;

    @InjectMocks
    private PlaceTraitManagementServiceImpl placeTraitManagementService;

    @SneakyThrows
    @Test
    void insertSingleTraitInPlace_placeNotFound_throwsPlaceNotFoundException() {
        // given
        UUID placeId = PlaceTestData.PLACE_ID;
        PlaceTraitRequestDTO DTO = PlaceTraitTestData.getPlaceTraitRequestDTO();
        given(placeRepository.findById(any())).willReturn(Optional.empty());

        // when & then
        assertThatExceptionOfType(PlaceNotFoundException.class)
                .isThrownBy(() -> placeTraitManagementService.insertSingleTraitInPlace(placeId, DTO))
                .withMessage("Place with id " + PlaceTestData.PLACE_ID + " not found.");
        verify(traitRepository, times(0)).findById(any());
        verify(placeTraitRepository, times(0)).findByPlaceIdAndTraitId(any(), any());
        verify(placeTraitRepository, times(0)).save(any());
    }

    @SneakyThrows
    @Test
    void insertSingleTraitInPlace_traitNotFound_throwsTraitNotFoundException() {
        // given
        UUID placeId = PlaceTestData.PLACE_ID;
        PlaceTraitRequestDTO DTO = PlaceTraitTestData.getPlaceTraitRequestDTO();
        given(placeRepository.findById(any())).willReturn(Optional.of(PlaceTestData.getPlace()));
        given(traitRepository.findById(any())).willReturn(Optional.empty());

        // when & then
        assertThatExceptionOfType(TraitNotFoundException.class)
                .isThrownBy(() -> placeTraitManagementService.insertSingleTraitInPlace(placeId, DTO))
                .withMessage("Trait with id " + TraitTestData.TRAIT_ID + " not found.");
        verify(placeTraitRepository, times(0)).findByPlaceIdAndTraitId(any(), any());
        verify(placeTraitRepository, times(0)).save(any());
    }

    @SneakyThrows
    @Test
    void insertSingleTraitInPlace_traitAlreadyPresentForPlace_throwsTraitAlreadyPresentForPlaceException() {
        // given
        UUID placeId = PlaceTestData.PLACE_ID;
        UUID traitId = TraitTestData.TRAIT_ID;
        PlaceTraitRequestDTO DTO = PlaceTraitTestData.getPlaceTraitRequestDTO();
        given(placeRepository.findById(any())).willReturn(Optional.of(PlaceTestData.getPlace()));
        given(traitRepository.findById(any())).willReturn(Optional.of(TraitTestData.getTrait()));
        given(placeTraitRepository.findByPlaceIdAndTraitId(any(), any())).willReturn(
                Optional.of(PlaceTraitTestData.getPlaceTrait()));

        // when & then
        assertThatExceptionOfType(TraitAlreadyPresentForPlaceException.class)
                .isThrownBy(() -> placeTraitManagementService.insertSingleTraitInPlace(placeId, DTO))
                .withMessage("Trait with id " + traitId + " already exists for place with id " + placeId + ".");
        verify(placeTraitRepository, times(0)).save(any());
    }

    @Test
    void insertSingleTraitInPlace_traitForPlaceInsertSuccessful_returnsMessage() {
        // given
        UUID placeId = PlaceTestData.PLACE_ID;
        PlaceTraitRequestDTO DTO = PlaceTraitTestData.getPlaceTraitRequestDTO();
        given(placeRepository.findById(any())).willReturn(Optional.of(PlaceTestData.getPlace()));
        given(traitRepository.findById(any())).willReturn(Optional.of(TraitTestData.getTrait()));
        given(placeTraitRepository.findByPlaceIdAndTraitId(any(), any())).willReturn(Optional.empty());

        // when
        String actualResult = placeTraitManagementService.insertSingleTraitInPlace(placeId, DTO);

        // then
        assertThat(actualResult).isEqualTo("Trait successfully inserted for place with id " + placeId + ".");
        verify(placeTraitRepository, times(1)).save(any());
    }

    @SneakyThrows
    @Test
    void batchInsertTraitsInPlace_placeNotFound_throwsPlaceNotFoundException() {
        // given
        UUID placeId = PlaceTestData.PLACE_ID;
        BatchInsertTraitsInPlace DTO = PlaceTraitTestData.getBatchInsertTraitsInPlace();
        given(placeRepository.findById(any())).willReturn(Optional.empty());

        // when & then
        assertThatExceptionOfType(PlaceNotFoundException.class)
                .isThrownBy(() -> placeTraitManagementService.batchInsertTraitsInPlace(placeId, DTO))
                .withMessage("Place with id " + PlaceTestData.PLACE_ID + " not found.");
        verify(traitRepository, times(0)).findAll();
        verify(placeTraitRepository, times(0)).findAllById(any());
        verify(placeTraitRepository, times(0)).findAllTraitIdsByPlaceId(any());
        verify(placeTraitRepository, times(0)).saveAll(any());
    }

    @SneakyThrows
    @Test
    void batchInsertTraitsInPlace_traitsNotFound_throwsTraitsNotFoundException() {
        // given
        UUID placeId = PlaceTestData.PLACE_ID;
        BatchInsertTraitsInPlace DTO = PlaceTraitTestData.getInvalidBatchInsertTraitsInPlace();
        given(placeRepository.findById(any())).willReturn(Optional.of(PlaceTestData.getPlace()));
        given(traitRepository.findAll()).willReturn(TraitTestData.getTraits());

        // when & then
        assertThatExceptionOfType(TraitsNotFoundException.class)
                .isThrownBy(() -> placeTraitManagementService.batchInsertTraitsInPlace(placeId, DTO))
                .withMessage("Traits with ids " + TraitTestData.TRAIT_INVALID_ID + " not found.");
        verify(placeTraitRepository, times(0)).findAllById(any());
        verify(placeTraitRepository, times(0)).findAllTraitIdsByPlaceId(any());
        verify(placeTraitRepository, times(0)).saveAll(any());
    }

    @SneakyThrows
    @Test
    void batchInsertTraitsInPlace_traitsAlreadyPresentForPlace_throwsTraitsAlreadyPresentForPlaceException() {
        // given
        UUID placeId = PlaceTestData.PLACE_ID;
        BatchInsertTraitsInPlace DTO = PlaceTraitTestData.getBatchInsertTraitsInPlace();
        given(placeRepository.findById(any())).willReturn(Optional.of(PlaceTestData.getPlace()));
        given(traitRepository.findAll()).willReturn(TraitTestData.getTraits());
        given(traitRepository.findAllById(any())).willReturn(TraitTestData.getTraits());
        given(placeTraitRepository.findAllTraitIdsByPlaceId(any())).willReturn(TraitTestData.getTraitIds());

        // when & then
        assertThatExceptionOfType(TraitsAlreadyPresentForPlaceException.class)
                .isThrownBy(() -> placeTraitManagementService.batchInsertTraitsInPlace(placeId, DTO))
                .withMessage(
                        "Traits with ids " + TraitTestData.TRAIT_ID + " already exists for place " + placeId + ".");
        verify(placeTraitRepository, times(0)).saveAll(any());
    }

    @Test
    void batchInsertTraitsInPlace_traitInsertedSuccessful_returnsMessage() {
        // given
        UUID placeId = PlaceTestData.PLACE_ID;
        BatchInsertTraitsInPlace DTO = PlaceTraitTestData.getBatchSuccessfulInsertTraitsInPlace();
        given(placeRepository.findById(any())).willReturn(Optional.of(PlaceTestData.getPlace()));
        given(traitRepository.findAll()).willReturn(TraitTestData.getTraits());
        given(traitRepository.findAllById(any())).willReturn(TraitTestData.getTraits());
        given(placeTraitRepository.findAllTraitIdsByPlaceId(any())).willReturn(TraitTestData.getTraitIds());

        // when
        String actualResult = placeTraitManagementService.batchInsertTraitsInPlace(placeId, DTO);

        // then
        assertThat(actualResult).isEqualTo("Inserted traits for place with id " + placeId + ".");
    }

    @SneakyThrows
    @Test
    void updateTraitForPlace_traitForPlaceNotFound_throwsPlaceTraitNotFoundException() {
        // given
        UUID placeId = PlaceTestData.PLACE_ID;
        PlaceTraitRequestDTO dto = PlaceTraitTestData.getPlaceTraitRequestDTO();
        given(placeTraitRepository.findByPlaceIdAndTraitId(any(), any())).willReturn(Optional.empty());

        // when & then
        assertThatExceptionOfType(TraitForPlaceNotFound.class)
                .isThrownBy(() -> placeTraitManagementService.updateTraitForPlace(placeId, dto))
                .withMessage("Trait with id " + placeId + " for place with id " + TraitTestData.TRAIT_ID + " not " +
                        "found.");
        verify(placeTraitRepository, times(0)).save(any());
    }

    @Test
    void updateTraitForPlace_successfulUpdateOfTraitForPlace_returnsMessage() {
        // given
        UUID placeId = PlaceTestData.PLACE_ID;
        PlaceTraitRequestDTO dto = PlaceTraitTestData.getPlaceTraitRequestDTO();
        given(placeTraitRepository.findByPlaceIdAndTraitId(any(), any())).willReturn(
                Optional.of(PlaceTraitTestData.getPlaceTrait()));

        // when
        String actualResult = placeTraitManagementService.updateTraitForPlace(placeId, dto);

        // then
        assertThat(actualResult).isEqualTo("Trait successfully updated for place with id " + placeId + ".");
        verify(placeTraitRepository, times(1)).save(any());
    }

    @SneakyThrows
    @Test
    void deleteSingleTraitInPlace_traitForPlaceNotFound_throwsPlaceTraitNotFoundException() {
        // given
        UUID placeId = PlaceTestData.PLACE_ID;
        UUID traitId = TraitTestData.TRAIT_ID;
        given(placeTraitRepository.findByPlaceIdAndTraitId(any(), any())).willReturn(
                Optional.empty());

        // when & then
        assertThatExceptionOfType(TraitForPlaceNotFound.class)
                .isThrownBy(() -> placeTraitManagementService.deleteSingleTraitInPlace(placeId, traitId))
                .withMessage("Trait with id " + placeId + " for place with id " + TraitTestData.TRAIT_ID + " not " +
                        "found.");
        verify(placeTraitRepository, times(0)).delete(any());
    }

    @Test
    void deleteSingleTraitInPlace_traitForPlaceDeleted_returnsMessage() {
        // given
        UUID placeId = PlaceTestData.PLACE_ID;
        UUID traitId = TraitTestData.TRAIT_ID;
        given(placeTraitRepository.findByPlaceIdAndTraitId(any(), any())).willReturn(
                Optional.of(PlaceTraitTestData.getPlaceTrait()));

        // when
        String actualResult = placeTraitManagementService.deleteSingleTraitInPlace(placeId, traitId);

        //then
        assertThat(actualResult).isEqualTo("Trait successfully deleted for place with id " + placeId + ".");
        verify(placeTraitRepository, times(1)).delete(any());
    }

    @SneakyThrows
    @Test
    void batchDeleteTraitsInPlace_placeNotFound_throwsPlaceNotFoundException() {
        // given
        BatchDeleteTraitsInPlace dto = PlaceTraitTestData.getBatchDeleteTraitsInPlaceDTO();
        given(placeRepository.existsById(any())).willReturn(false);

        // when & then
        assertThatExceptionOfType(PlaceNotFoundException.class)
                .isThrownBy(() -> placeTraitManagementService.batchDeleteTraitsInPlace(PlaceTestData.PLACE_ID, dto))
                .withMessage("Place with id " + PlaceTestData.PLACE_ID + " not found.");
        verify(placeTraitRepository, times(0)).findAllByPlaceId(any());
        verify(placeTraitRepository, times(0)).deleteAll(any());
    }

    @SneakyThrows
    @Test
    void batchDeleteTraitsInPlace_traitsNotFound_throwsTraitsNotFoundException() {
        // given
        BatchDeleteTraitsInPlace dto = PlaceTraitTestData.getInvalidBatchDeleteTraitsInPlaceDTO();
        List<PlaceTrait> placeTraits =
                PlaceTraitTestData.getPlaceTraits().stream().limit(1).toList();
        given(placeRepository.existsById(any())).willReturn(true);
        given(placeTraitRepository.findAllByPlaceId(any())).willReturn(placeTraits);

        // when & then
        assertThatExceptionOfType(TraitsNotFoundException.class)
                .isThrownBy(() -> placeTraitManagementService.batchDeleteTraitsInPlace(PlaceTestData.PLACE_ID, dto))
                .withMessage("Traits with ids " + TraitTestData.TRAIT_SECOND_ID + " not found.");
        verify(placeTraitRepository, times(0)).deleteAll(any());
    }

    @Test
    void batchDeleteTraitsInPlace_traitsForPlaceDeletes_returnsMessage() {
        // given
        UUID placeId = PlaceTestData.PLACE_ID;
        BatchDeleteTraitsInPlace dto = PlaceTraitTestData.getBatchDeleteTraitsInPlaceDTO();
        given(placeRepository.existsById(any())).willReturn(true);
        given(placeTraitRepository.findAllByPlaceId(any())).willReturn(PlaceTraitTestData.getPlaceTraits());

        // when
        String actualResult = placeTraitManagementService.batchDeleteTraitsInPlace(placeId, dto);

        // then
        assertThat(actualResult).isEqualTo("Deleted 1 traits for place with id " + placeId + ".");
        verify(placeTraitRepository, times(1)).deleteAll(any());
    }
}
