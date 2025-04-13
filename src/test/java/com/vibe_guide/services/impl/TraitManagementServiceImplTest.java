package com.vibe_guide.services.impl;

import com.vibe_guide.dtos.TraitInsertRequestDTO;
import com.vibe_guide.dtos.TraitUpdateRequestDTO;
import com.vibe_guide.enums.TraitType;
import com.vibe_guide.exceptions.TraitAlreadyPresentException;
import com.vibe_guide.exceptions.TraitNotFoundException;
import com.vibe_guide.repositories.TraitRepository;
import com.vibe_guide.utils.TraitTestData;
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
class TraitManagementServiceImplTest {
    @Mock
    TraitRepository traitRepository;

    @InjectMocks
    TraitManagementServiceImpl traitManagementService;

    @SneakyThrows
    @Test
    void insertTrait_traitAlreadyExists_throwsTraitAlreadyPresentException() {
        // given
        TraitType traitType = TraitTestData.TRAIT_TYPE;
        String traitName = TraitTestData.TRAIT_NAME;
        given(traitRepository.getTraitByTraitTypeAndName(traitType, traitName))
                .willReturn(Optional.of(TraitTestData.getTrait()));

        // when & then
        TraitInsertRequestDTO requestDTO = TraitTestData.getTraitInsertRequestDTO();
        assertThatExceptionOfType(TraitAlreadyPresentException.class)
                .isThrownBy(() -> traitManagementService.insertTrait(requestDTO))
                .withMessage(
                        "Trait with trait type " + traitType + " and name " + traitName +
                                " already present in the database.");
        verify(traitRepository, times(0)).save(any());
    }

    @SneakyThrows
    @Test
    void insertTrait_traitInsertSuccessful_throwsTraitAlreadyPresentException() {
        // given
        given(traitRepository.getTraitByTraitTypeAndName(TraitTestData.TRAIT_TYPE,
                TraitTestData.TRAIT_NAME)).willReturn(Optional.empty());

        // when
        String actualResult = traitManagementService.insertTrait(TraitTestData.getTraitInsertRequestDTO());

        // then
        assertThat(actualResult).isEqualTo(TraitTestData.TRAIT_INSERT_MESSAGE);
    }

    @SneakyThrows
    @Test
    void updateTrait_traitAlreadyExists_throwsTraitAlreadyPresentException() {
        // given
        TraitType traitType = TraitTestData.TRAIT_TYPE;
        String traitName = TraitTestData.TRAIT_NAME;
        given(traitRepository.getTraitByTraitTypeAndName(traitType, traitName))
                .willReturn(Optional.of(TraitTestData.getTrait()));

        // when & then
        TraitUpdateRequestDTO requestDTO = TraitTestData.getTraitUpdateRequestDTO();
        assertThatExceptionOfType(TraitAlreadyPresentException.class)
                .isThrownBy(() -> traitManagementService.updateTrait(requestDTO))
                .withMessage(
                        "Trait with trait type " + traitType + " and name " + traitName +
                                " already present in the database.");
        verify(traitRepository, times(0)).findById(any());
        verify(traitRepository, times(0)).save(any());
    }

    @SneakyThrows
    @Test
    void updateTrait_traitNotFound_throwsTraitNotFoundException() {
        // given
        given(traitRepository.getTraitByTraitTypeAndName(TraitTestData.TRAIT_TYPE, TraitTestData.TRAIT_NAME))
                .willReturn(Optional.empty());
        given(traitRepository.findById(any())).willReturn(Optional.empty());

        // when & then
        TraitUpdateRequestDTO requestDTO = TraitTestData.getTraitUpdateRequestDTO();
        assertThatExceptionOfType(TraitNotFoundException.class)
                .isThrownBy(() -> traitManagementService.updateTrait(requestDTO))
                .withMessage("Trait with id " + TraitTestData.TRAIT_ID + " not found.");
        verify(traitRepository, times(0)).save(any());
    }

    @SneakyThrows
    @Test
    void updateTrait_traitUpdateSuccessful_throwsTraitAlreadyPresentException() {
        // given
        given(traitRepository.getTraitByTraitTypeAndName(TraitTestData.TRAIT_TYPE, TraitTestData.TRAIT_NAME))
                .willReturn(Optional.empty());
        given(traitRepository.findById(TraitTestData.TRAIT_ID)).willReturn(Optional.of(TraitTestData.getTrait()));

        // when
        String actualResult = traitManagementService.updateTrait(TraitTestData.getTraitUpdateRequestDTO());

        // then
        assertThat(actualResult).isEqualTo(TraitTestData.TRAIT_UPDATE_MESSAGE);
    }

    @SneakyThrows
    @Test
    void deleteTrait_traitNotFound_throwsTraitNotFoundException() {
        // given
        UUID traitId = TraitTestData.TRAIT_ID;
        given(traitRepository.findById(any())).willReturn(Optional.empty());

        // when & then
        assertThatExceptionOfType(TraitNotFoundException.class)
                .isThrownBy(() -> traitManagementService.deleteTrait(traitId))
                .withMessage("Trait with id " + traitId + " not found.");
        verify(traitRepository, times(0)).deleteById(any());
    }

    @SneakyThrows
    @Test
    void deleteTrait_traitDeleteSuccessful_throwsTraitNotFoundException() {
        // given
        given(traitRepository.findById(any())).willReturn(Optional.of(TraitTestData.getTrait()));

        // when
        String actualResult = traitManagementService.deleteTrait(TraitTestData.TRAIT_ID);

        // then
        assertThat(actualResult).isEqualTo(TraitTestData.TRAIT_DELETE_MESSAGE);
    }
}