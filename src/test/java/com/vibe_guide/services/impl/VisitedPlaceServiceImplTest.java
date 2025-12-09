package com.vibe_guide.services.impl;

import com.vibe_guide.data.TestDataFactory;
import com.vibe_guide.dtos.VisitedPlaceResponseDTO;
import com.vibe_guide.entities.Place;
import com.vibe_guide.entities.User;
import com.vibe_guide.entities.VisitedPlace;
import com.vibe_guide.entities.composite_keys.VisitedPlaceId;
import com.vibe_guide.repositories.PlaceRepository;
import com.vibe_guide.repositories.UserRepository;
import com.vibe_guide.repositories.VisitedPlaceRepository;
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
class VisitedPlaceServiceImplTest {

    @Mock
    private VisitedPlaceRepository visitedPlaceRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PlaceRepository placeRepository;

    @InjectMocks
    private VisitedPlaceServiceImpl service;

    @Test
    void getVisitedPlacesByUserIdReturnsRepositoryProjection() {
        UUID userId = TestDataFactory.uuid();
        List<VisitedPlaceResponseDTO> expected = List.of(TestDataFactory.visitedPlaceResponseDto(TestDataFactory.uuid()));
        when(userRepository.findById(userId)).thenReturn(Optional.of(TestDataFactory.user(userId)));
        when(visitedPlaceRepository.findVisitedPlacesByUser(userId)).thenReturn(expected);

        List<VisitedPlaceResponseDTO> result = service.getVisitedPlacesByUserId(userId);

        assertEquals(expected, result);
    }

    @Test
    void manageVisitedPlaceCreatesRecordWhenMissing() {
        UUID userId = TestDataFactory.uuid();
        UUID placeId = TestDataFactory.uuid();
        User user = TestDataFactory.user(userId);
        Place place = TestDataFactory.place(placeId);
        when(placeRepository.findById(placeId)).thenReturn(Optional.of(place));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(visitedPlaceRepository.findById(new VisitedPlaceId(userId, placeId))).thenReturn(Optional.empty());

        String result = service.manageVisitedPlace(userId, placeId);

        assertEquals("Place marked as visited.", result);
        verify(visitedPlaceRepository).save(any(VisitedPlace.class));
    }

    @Test
    void manageVisitedPlaceDeletesRecordWhenExists() {
        UUID userId = TestDataFactory.uuid();
        UUID placeId = TestDataFactory.uuid();
        User user = TestDataFactory.user(userId);
        Place place = TestDataFactory.place(placeId);
        VisitedPlace existing = TestDataFactory.visitedPlace(user, place);
        VisitedPlaceId id = new VisitedPlaceId(userId, placeId);
        when(placeRepository.findById(placeId)).thenReturn(Optional.of(place));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(visitedPlaceRepository.findById(id)).thenReturn(Optional.of(existing));

        String result = service.manageVisitedPlace(userId, placeId);

        assertEquals("Place removed from visited list.", result);
        verify(visitedPlaceRepository).delete(existing);
    }
}
