package com.vibe_guide.services.impl;

import com.vibe_guide.data.TestDataFactory;
import com.vibe_guide.dtos.OfferInsertDTO;
import com.vibe_guide.dtos.OfferUpdateDTO;
import com.vibe_guide.entities.Offer;
import com.vibe_guide.entities.Place;
import com.vibe_guide.repositories.OfferRepository;
import com.vibe_guide.repositories.PlaceRepository;
import com.vibe_guide.utils.OfferResponseMessages;
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
class OfferManagementServiceImplTest {

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private PlaceRepository placeRepository;

    @InjectMocks
    private OfferManagementServiceImpl service;

    @Test
    void insertOfferPersistsEntity() {
        OfferInsertDTO dto = TestDataFactory.offerInsertDto(TestDataFactory.uuid());
        Place place = TestDataFactory.place(dto.placeId());
        when(placeRepository.findById(dto.placeId())).thenReturn(Optional.of(place));

        String result = service.insertOffer(dto);

        assertEquals(OfferResponseMessages.OFFER_INSERT_MESSAGE, result);
        verify(offerRepository).save(any(Offer.class));
    }

    @Test
    void updateDailyOfferUpdatesExistingEntity() {
        OfferUpdateDTO dto = TestDataFactory.offerUpdateDto(TestDataFactory.uuid(), TestDataFactory.uuid());
        Place place = TestDataFactory.place(dto.placeId());
        Offer offer = TestDataFactory.offer(dto.dailyOfferId(), place);
        when(placeRepository.findById(dto.placeId())).thenReturn(Optional.of(place));
        when(offerRepository.findById(dto.dailyOfferId())).thenReturn(Optional.of(offer));

        String result = service.updateDailyOffer(dto);

        assertEquals(OfferResponseMessages.OFFER_UPDATE_MESSAGE, result);
        verify(offerRepository).save(offer);
    }

    @Test
    void deleteDailyOfferRemovesEntity() {
        UUID offerId = TestDataFactory.uuid();
        Offer offer = TestDataFactory.offer(offerId, TestDataFactory.place(TestDataFactory.uuid()));
        when(offerRepository.findById(offerId)).thenReturn(Optional.of(offer));

        String result = service.deleteDailyOffer(offerId);

        assertEquals(OfferResponseMessages.OFFER_DELETE_MESSAGE, result);
        verify(offerRepository).delete(offer);
    }
}
