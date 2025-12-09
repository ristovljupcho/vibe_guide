package com.vibe_guide.services.impl;

import com.vibe_guide.converters.OfferConverter;
import com.vibe_guide.data.TestDataFactory;
import com.vibe_guide.dtos.OfferResponseDTO;
import com.vibe_guide.entities.Offer;
import com.vibe_guide.repositories.OfferRepository;
import com.vibe_guide.repositories.PlaceRepository;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OfferQueryServiceImplTest {

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private OfferConverter offerConverter;

    @Mock
    private PlaceRepository placeRepository;

    @InjectMocks
    private OfferQueryServiceImpl service;

    @Test
    void getActiveOffersByPlaceIdValidatesPlaceAndMapsEntities() {
        UUID placeId = TestDataFactory.uuid();
        Offer offer = TestDataFactory.offer(TestDataFactory.uuid(), TestDataFactory.place(placeId));
        OfferResponseDTO dto = TestDataFactory.offerResponseDto();
        when(placeRepository.findById(placeId)).thenReturn(Optional.of(TestDataFactory.place(placeId)));
        when(offerRepository.findDailyOffersByPlaceId(any(), eq(placeId))).thenReturn(List.of(offer));
        when(offerConverter.toOfferResponseDTO(offer)).thenReturn(dto);

        List<OfferResponseDTO> result = service.getActiveOffersByPlaceId(placeId);

        assertEquals(List.of(dto), result);
    }

    @Test
    void getAllUpcomingOffersReturnsMappedDtos() {
        Offer offer = TestDataFactory.offer(TestDataFactory.uuid(), TestDataFactory.place(TestDataFactory.uuid()));
        OfferResponseDTO dto = TestDataFactory.offerResponseDto();
        when(offerRepository.findAllUpcomingOffers(any())).thenReturn(List.of(offer));
        when(offerConverter.toOfferResponseDTO(offer)).thenReturn(dto);

        List<OfferResponseDTO> result = service.getAllUpcomingOffers();

        assertEquals(List.of(dto), result);
    }

    @Test
    void getUpcomingOffersByPlaceIdChecksExistence() {
        UUID placeId = TestDataFactory.uuid();
        Offer offer = TestDataFactory.offer(TestDataFactory.uuid(), TestDataFactory.place(placeId));
        OfferResponseDTO dto = TestDataFactory.offerResponseDto();
        when(placeRepository.findById(placeId)).thenReturn(Optional.of(TestDataFactory.place(placeId)));
        when(offerRepository.findUpcomingOffersByPlaceId(any(), eq(placeId))).thenReturn(List.of(offer));
        when(offerConverter.toOfferResponseDTO(offer)).thenReturn(dto);

        List<OfferResponseDTO> result = service.getUpcomingOffersByPlaceId(placeId);

        assertEquals(List.of(dto), result);
    }
}
