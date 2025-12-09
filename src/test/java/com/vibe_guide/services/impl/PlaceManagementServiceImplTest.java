package com.vibe_guide.services.impl;

import com.vibe_guide.converters.PlaceConverter;
import com.vibe_guide.dtos.PlaceRequestDTO;
import com.vibe_guide.dtos.PlaceResponseDTO;
import com.vibe_guide.entities.Place;
import com.vibe_guide.enums.PriceLevel;
import com.vibe_guide.enums.PrimaryType;
import com.vibe_guide.exceptions.PlaceAlreadyExistsException;
import com.vibe_guide.repositories.PlaceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PlaceManagementServiceImplTest {

    @Mock
    private PlaceRepository placeRepository;

    @Mock
    private PlaceConverter placeConverter;

    @InjectMocks
    private PlaceManagementServiceImpl placeManagementService;

    @Test
    void insertPlace_placeAlreadyExists_throwsPlaceAlreadyExistsException() {
        // given
        PlaceRequestDTO requestDTO = createPlaceRequestDTO();
        UUID placeId = requestDTO.placeId();
        given(placeRepository.existsById(placeId)).willReturn(true);

        // when & then
        assertThatExceptionOfType(PlaceAlreadyExistsException.class)
                .isThrownBy(() -> placeManagementService.insertPlace(requestDTO))
                .withMessage("Place with id " + placeId + " already exists.");

        verify(placeRepository, never()).save(any());
        verify(placeConverter, never()).toPlaceResponseDTO(any());
    }

    @Test
    void insertPlace_validRequest_persistsPlaceAndReturnsResponse() {
        // given
        PlaceRequestDTO requestDTO = createPlaceRequestDTO();
        UUID placeId = requestDTO.placeId();
        PlaceResponseDTO expectedResponse = new PlaceResponseDTO(
                requestDTO.name(),
                requestDTO.description(),
                requestDTO.mapsUri(),
                requestDTO.phoneNumber(),
                requestDTO.address(),
                0.0,
                requestDTO.menuLink(),
                requestDTO.primaryType(),
                requestDTO.priceLevel()
        );

        given(placeRepository.existsById(placeId)).willReturn(false);
        given(placeRepository.save(any(Place.class))).willAnswer(invocation -> invocation.getArgument(0));
        given(placeConverter.toPlaceResponseDTO(any(Place.class))).willReturn(expectedResponse);

        // when
        PlaceResponseDTO actualResponse = placeManagementService.insertPlace(requestDTO);

        // then
        assertThat(actualResponse).isEqualTo(expectedResponse);

        ArgumentCaptor<Place> placeCaptor = ArgumentCaptor.forClass(Place.class);
        verify(placeRepository).save(placeCaptor.capture());
        Place persistedPlace = placeCaptor.getValue();

        assertThat(persistedPlace.getId()).isEqualTo(placeId);
        assertThat(persistedPlace.getName()).isEqualTo(requestDTO.name());
        assertThat(persistedPlace.getDescription()).isEqualTo(requestDTO.description());
        assertThat(persistedPlace.getMapsUri()).isEqualTo(requestDTO.mapsUri());
        assertThat(persistedPlace.getPhoneNumber()).isEqualTo(requestDTO.phoneNumber());
        assertThat(persistedPlace.getAddress()).isEqualTo(requestDTO.address());
        assertThat(persistedPlace.getMenuLink()).isEqualTo(requestDTO.menuLink());
        assertThat(persistedPlace.getPrimaryType()).isEqualTo(requestDTO.primaryType());
        assertThat(persistedPlace.getPriceLevel()).isEqualTo(requestDTO.priceLevel());
    }

    private PlaceRequestDTO createPlaceRequestDTO() {
        return new PlaceRequestDTO(
                UUID.randomUUID(),
                "Test Place",
                "Test Description",
                "https://maps.example.com",
                "+123456789",
                "123 Test Street",
                "https://menu.example.com",
                PrimaryType.BAR,
                PriceLevel.MODERATE,
                List.of()
        );
    }
}
