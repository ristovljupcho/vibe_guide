package com.vibe_guide.services.impl;

import com.vibe_guide.data.TestDataFactory;
import com.vibe_guide.dtos.FavouritePlaceResponseDTO;
import com.vibe_guide.entities.FavouritePlace;
import com.vibe_guide.entities.Place;
import com.vibe_guide.entities.User;
import com.vibe_guide.entities.composite_keys.FavouritePlaceId;
import com.vibe_guide.repositories.FavouritePlaceRepository;
import com.vibe_guide.repositories.PlaceRepository;
import com.vibe_guide.repositories.UserRepository;
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
class FavouritePlaceServiceImplTest {

    @Mock
    private FavouritePlaceRepository favouritePlaceRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PlaceRepository placeRepository;

    @InjectMocks
    private FavouritePlaceServiceImpl service;

    @Test
    void getFavouritePlacesByUserIdReturnsProjection() {
        UUID userId = TestDataFactory.uuid();
        when(userRepository.findById(userId)).thenReturn(Optional.of(TestDataFactory.user(userId)));
        List<FavouritePlaceResponseDTO> expected = List.of(
                TestDataFactory.favouritePlaceResponseDto(TestDataFactory.uuid()));
        when(favouritePlaceRepository.getFavouritePlacesByUserId(userId)).thenReturn(expected);

        List<FavouritePlaceResponseDTO> result = service.getFavouritePlacesByUserId(userId);

        assertEquals(expected, result);
    }

    @Test
    void manageFavouritePlaceAddsEntryWhenMissing() {
        UUID userId = TestDataFactory.uuid();
        UUID placeId = TestDataFactory.uuid();
        User user = TestDataFactory.user(userId);
        Place place = TestDataFactory.place(placeId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(placeRepository.findById(placeId)).thenReturn(Optional.of(place));
        when(favouritePlaceRepository.findById(new FavouritePlaceId(userId, placeId))).thenReturn(Optional.empty());

        String result = service.manageFavouritePlace(userId, placeId);

        assertEquals("Place added to favourites.", result);
        verify(favouritePlaceRepository).save(any(FavouritePlace.class));
    }

    @Test
    void manageFavouritePlaceRemovesEntryWhenPresent() {
        UUID userId = TestDataFactory.uuid();
        UUID placeId = TestDataFactory.uuid();
        User user = TestDataFactory.user(userId);
        Place place = TestDataFactory.place(placeId);
        FavouritePlace existing = TestDataFactory.favouritePlace(user, place);
        FavouritePlaceId id = new FavouritePlaceId(userId, placeId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(placeRepository.findById(placeId)).thenReturn(Optional.of(place));
        when(favouritePlaceRepository.findById(id)).thenReturn(Optional.of(existing));

        String result = service.manageFavouritePlace(userId, placeId);

        assertEquals("Place removed from favourites.", result);
        verify(favouritePlaceRepository).delete(existing);
    }
}
