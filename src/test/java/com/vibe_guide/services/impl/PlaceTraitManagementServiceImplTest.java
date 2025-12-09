package com.vibe_guide.services.impl;

import com.vibe_guide.data.TestDataFactory;
import com.vibe_guide.dtos.BatchDeleteTraitsInPlace;
import com.vibe_guide.dtos.PlaceTraitRequestDTO;
import com.vibe_guide.entities.Place;
import com.vibe_guide.entities.PlaceTrait;
import com.vibe_guide.entities.Trait;
import com.vibe_guide.repositories.PlaceRepository;
import com.vibe_guide.repositories.PlaceTraitRepository;
import com.vibe_guide.repositories.TraitRepository;
import com.vibe_guide.utils.PlaceTraitResponseMessages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlaceTraitManagementServiceImplTest {

    @Mock
    private PlaceRepository placeRepository;

    @Mock
    private TraitRepository traitRepository;

    @Mock
    private PlaceTraitRepository placeTraitRepository;

    @InjectMocks
    private PlaceTraitManagementServiceImpl service;

    @Test
    void insertSingleTraitInPlacePersistsEntity() {
        Place place = TestDataFactory.place(TestDataFactory.uuid());
        Trait trait = TestDataFactory.trait(TestDataFactory.uuid());
        PlaceTraitRequestDTO request = TestDataFactory.placeTraitRequestDto(place.getId(), trait.getId());

        when(placeRepository.findById(place.getId())).thenReturn(Optional.of(place));
        when(traitRepository.findById(trait.getId())).thenReturn(Optional.of(trait));
        when(placeTraitRepository.findByPlaceIdAndTraitId(place.getId(), trait.getId())).thenReturn(Optional.empty());

        String result = service.insertSingleTraitInPlace(request);

        String expectedMessage = String.format(PlaceTraitResponseMessages.PLACE_TRAIT_INSERT_MESSAGE, place.getId());
        assertEquals(expectedMessage, result);
        verify(placeTraitRepository).save(any(PlaceTrait.class));
    }

    @Test
    void batchDeleteTraitsInPlaceRemovesEntities() {
        UUID placeId = TestDataFactory.uuid();
        List<UUID> traitIds = List.of(TestDataFactory.uuid(), TestDataFactory.uuid());
        BatchDeleteTraitsInPlace request = TestDataFactory.batchDeleteTraitsRequest(placeId, traitIds);

        when(placeRepository.existsById(placeId)).thenReturn(true);

        List<PlaceTrait> existing = traitIds.stream()
                .map(traitId -> {
                    Place place = TestDataFactory.place(placeId);
                    Trait trait = TestDataFactory.trait(traitId);
                    return TestDataFactory.placeTrait(TestDataFactory.uuid(), place, trait);
                })
                .toList();
        when(placeTraitRepository.findAllByPlaceId(placeId)).thenReturn(existing);

        String result = service.batchDeleteTraitsInPlace(request);

        assertEquals(
                String.format(PlaceTraitResponseMessages.PLACE_TRAIT_BATCH_DELETE_MESSAGE, existing.size(), placeId),
                result
        );
        verify(placeTraitRepository).deleteAll(existing);
    }
}
