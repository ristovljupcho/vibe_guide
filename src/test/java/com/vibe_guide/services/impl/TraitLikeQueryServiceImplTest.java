package com.vibe_guide.services.impl;

import com.vibe_guide.data.TestDataFactory;
import com.vibe_guide.dtos.TraitResponseDTO;
import com.vibe_guide.repositories.TraitLikeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TraitLikeQueryServiceImplTest {

    @Mock
    private TraitLikeRepository traitLikeRepository;

    @InjectMocks
    private TraitLikeQueryServiceImpl service;

    @Test
    void findAllByPlaceAndUserDelegatesToRepository() {
        UUID placeId = TestDataFactory.uuid();
        UUID userId = TestDataFactory.uuid();
        List<TraitResponseDTO> expected = List.of(TestDataFactory.traitResponseDto(TestDataFactory.uuid()));

        when(traitLikeRepository.findAllByUserIdAndPlaceId(userId, placeId)).thenReturn(expected);

        List<TraitResponseDTO> result = service.findAllByPlaceAndUser(placeId, userId);

        assertEquals(expected, result);
        verify(traitLikeRepository).findAllByUserIdAndPlaceId(userId, placeId);
    }
}
