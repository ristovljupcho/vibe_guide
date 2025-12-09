package com.vibe_guide.services.impl;

import com.vibe_guide.data.TestDataFactory;
import com.vibe_guide.dtos.TraitLikeRequestDTO;
import com.vibe_guide.entities.Place;
import com.vibe_guide.entities.PlaceTrait;
import com.vibe_guide.entities.Trait;
import com.vibe_guide.entities.TraitLike;
import com.vibe_guide.entities.User;
import com.vibe_guide.repositories.PlaceRepository;
import com.vibe_guide.repositories.PlaceTraitRepository;
import com.vibe_guide.repositories.TraitLikeRepository;
import com.vibe_guide.repositories.UserRepository;
import com.vibe_guide.utils.TraitLikeResponseMessages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TraitLikeManagementServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PlaceRepository placeRepository;

    @Mock
    private PlaceTraitRepository placeTraitRepository;

    @Mock
    private TraitLikeRepository traitLikeRepository;

    @InjectMocks
    private TraitLikeManagementServiceImpl service;

    @Test
    void likeTraitsCreatesNewLikes() {
        UUID userId = TestDataFactory.uuid();
        UUID placeId = TestDataFactory.uuid();
        UUID traitId = TestDataFactory.uuid();
        TraitLikeRequestDTO request = TestDataFactory.traitLikeRequestDto(userId, placeId, List.of(traitId));
        User user = TestDataFactory.user(userId);
        Place place = TestDataFactory.place(placeId);
        Trait trait = TestDataFactory.trait(traitId);
        PlaceTrait placeTrait = TestDataFactory.placeTrait(TestDataFactory.uuid(), place, trait);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(placeRepository.findById(placeId)).thenReturn(Optional.of(place));
        when(placeTraitRepository.findAllByPlaceIdAndTraitIdIn(placeId, List.of(traitId))).thenReturn(List.of(placeTrait));
        when(traitLikeRepository.findAllByUserIdAndPlaceTraitIdIn(userId, List.of(placeTrait.getId()))).thenReturn(List.of());

        String result = service.likeTraits(request);

        assertEquals(TraitLikeResponseMessages.TRAIT_LIKE_SUCCESSFUL_INSERT, result);
        verify(traitLikeRepository).saveAll(anyCollection());
        verify(placeTraitRepository).saveAll(anyCollection());
    }

    @Test
    void unlikeTraitsDeletesExistingLikes() {
        UUID userId = TestDataFactory.uuid();
        UUID placeId = TestDataFactory.uuid();
        UUID traitId = TestDataFactory.uuid();
        TraitLikeRequestDTO request = TestDataFactory.traitLikeRequestDto(userId, placeId, List.of(traitId));
        User user = TestDataFactory.user(userId);
        Place place = TestDataFactory.place(placeId);
        Trait trait = TestDataFactory.trait(traitId);
        PlaceTrait placeTrait = TestDataFactory.placeTrait(TestDataFactory.uuid(), place, trait);
        TraitLike existingLike = TestDataFactory.traitLike(placeTrait, user);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(placeRepository.findById(placeId)).thenReturn(Optional.of(place));
        when(placeTraitRepository.findAllByPlaceIdAndTraitIdIn(placeId, List.of(traitId))).thenReturn(List.of(placeTrait));
        when(traitLikeRepository.findAllByUserIdAndPlaceTraitIdIn(userId, List.of(placeTrait.getId())))
                .thenReturn(List.of(existingLike));

        String result = service.unlikeTraits(request);

        assertEquals(TraitLikeResponseMessages.TRAIT_LIKE_SUCCESSFUL_DELETE, result);
        verify(traitLikeRepository).deleteAll(List.of(existingLike));
        verify(placeTraitRepository).saveAll(anyCollection());
    }
}
