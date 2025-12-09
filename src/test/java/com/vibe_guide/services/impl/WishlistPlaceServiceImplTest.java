package com.vibe_guide.services.impl;

import com.vibe_guide.data.TestDataFactory;
import com.vibe_guide.dtos.WishlistPlaceResponseDTO;
import com.vibe_guide.entities.Place;
import com.vibe_guide.entities.User;
import com.vibe_guide.entities.WishlistPlace;
import com.vibe_guide.entities.composite_keys.WishlistPlaceId;
import com.vibe_guide.repositories.PlaceRepository;
import com.vibe_guide.repositories.UserRepository;
import com.vibe_guide.repositories.WishlistPlaceRepository;
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
class WishlistPlaceServiceImplTest {

    @Mock
    private WishlistPlaceRepository wishlistRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PlaceRepository placeRepository;

    @InjectMocks
    private WishlistPlaceServiceImpl service;

    @Test
    void getWishlistPlacesByUserIdReturnsProjection() {
        UUID userId = TestDataFactory.uuid();
        when(userRepository.findById(userId)).thenReturn(Optional.of(TestDataFactory.user(userId)));
        List<WishlistPlaceResponseDTO> expected = List.of(
                TestDataFactory.wishlistPlaceResponseDto(TestDataFactory.uuid()));
        when(wishlistRepository.getWishlistByUserId(userId)).thenReturn(expected);

        List<WishlistPlaceResponseDTO> result = service.getWishlistPlacesByUserId(userId);

        assertEquals(expected, result);
    }

    @Test
    void manageWishlistPlaceAddsEntryWhenMissing() {
        UUID userId = TestDataFactory.uuid();
        UUID placeId = TestDataFactory.uuid();
        User user = TestDataFactory.user(userId);
        Place place = TestDataFactory.place(placeId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(placeRepository.findById(placeId)).thenReturn(Optional.of(place));
        when(wishlistRepository.findById(new WishlistPlaceId(userId, placeId))).thenReturn(Optional.empty());

        String result = service.manageWishlistPlace(userId, placeId);

        assertEquals("Place added to wishlist.", result);
        verify(wishlistRepository).save(any(WishlistPlace.class));
    }

    @Test
    void manageWishlistPlaceRemovesEntryWhenPresent() {
        UUID userId = TestDataFactory.uuid();
        UUID placeId = TestDataFactory.uuid();
        User user = TestDataFactory.user(userId);
        Place place = TestDataFactory.place(placeId);
        WishlistPlace existing = TestDataFactory.wishlistPlace(user, place);
        WishlistPlaceId id = new WishlistPlaceId(userId, placeId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(placeRepository.findById(placeId)).thenReturn(Optional.of(place));
        when(wishlistRepository.findById(id)).thenReturn(Optional.of(existing));

        String result = service.manageWishlistPlace(userId, placeId);

        assertEquals("Place removed from wishlist.", result);
        verify(wishlistRepository).delete(existing);
    }
}
