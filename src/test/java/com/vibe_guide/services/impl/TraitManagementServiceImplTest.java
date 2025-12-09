package com.vibe_guide.services.impl;

import com.vibe_guide.data.TestDataFactory;
import com.vibe_guide.dtos.TraitInsertRequestDTO;
import com.vibe_guide.dtos.TraitUpdateRequestDTO;
import com.vibe_guide.entities.Trait;
import com.vibe_guide.enums.TraitType;
import com.vibe_guide.repositories.TraitRepository;
import com.vibe_guide.utils.TraitResponseMessages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TraitManagementServiceImplTest {

    @Mock
    private TraitRepository traitRepository;

    @InjectMocks
    private TraitManagementServiceImpl service;

    @Test
    void insertTraitPersistsEntity() {
        TraitInsertRequestDTO request = TestDataFactory.traitInsertRequestDto();
        when(traitRepository.getTraitByTraitTypeAndName(request.traitType(), request.name())).thenReturn(Optional.empty());

        String result = service.insertTrait(request);

        assertEquals(String.format(TraitResponseMessages.TRAIT_INSERT_MESSAGE, request.traitType(), request.name()),
                result);
        verify(traitRepository).save(any(Trait.class));
    }

    @Test
    void updateTraitUsesExistingEntity() {
        Trait trait = TestDataFactory.trait(TestDataFactory.uuid());
        TraitUpdateRequestDTO request = new TraitUpdateRequestDTO(trait.getId(), TraitType.FOOD, "Updated");
        when(traitRepository.getTraitByTraitTypeAndName(request.traitType(), request.name())).thenReturn(Optional.empty());
        when(traitRepository.findById(trait.getId())).thenReturn(Optional.of(trait));

        String result = service.updateTrait(request);

        assertEquals(String.format(TraitResponseMessages.TRAIT_UPDATE_MESSAGE, trait.getId()), result);
        verify(traitRepository).save(trait);
    }

    @Test
    void deleteTraitRemovesEntity() {
        UUID traitId = TestDataFactory.uuid();
        Trait trait = TestDataFactory.trait(traitId);
        when(traitRepository.findById(traitId)).thenReturn(Optional.of(trait));

        String result = service.deleteTrait(traitId);

        assertEquals(String.format(TraitResponseMessages.TRAIT_DELETE_MESSAGE, traitId), result);
        verify(traitRepository).deleteById(traitId);
    }
}
