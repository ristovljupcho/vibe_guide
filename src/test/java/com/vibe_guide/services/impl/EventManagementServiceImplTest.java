package com.vibe_guide.services.impl;

import com.vibe_guide.data.TestDataFactory;
import com.vibe_guide.dtos.EventInsertRequestDTO;
import com.vibe_guide.dtos.EventUpdateRequestDTO;
import com.vibe_guide.entities.Event;
import com.vibe_guide.entities.Place;
import com.vibe_guide.repositories.EventRepository;
import com.vibe_guide.repositories.PlaceRepository;
import com.vibe_guide.services.EventGalleryManagementService;
import com.vibe_guide.utils.EventResponseMessages;
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
class EventManagementServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private PlaceRepository placeRepository;

    @Mock
    private EventGalleryManagementService eventGalleryManagementService;

    @InjectMocks
    private EventManagementServiceImpl service;

    @Test
    void insertEventSavesEntityAndUploadsImages() {
        EventInsertRequestDTO dto = TestDataFactory.eventInsertRequestDto(TestDataFactory.uuid());
        Place place = TestDataFactory.place(dto.placeId());
        when(placeRepository.findById(dto.placeId())).thenReturn(Optional.of(place));
        when(eventRepository.save(any(Event.class))).thenAnswer(invocation -> {
            Event saved = invocation.getArgument(0);
            saved.setId(TestDataFactory.uuid());
            return saved;
        });

        String result = service.insertEvent(dto);

        assertEquals(EventResponseMessages.EVENT_INSERT_MESSAGE, result);
        verify(eventGalleryManagementService).addImagesToEvent(any(UUID.class), any());
    }

    @Test
    void updateEventModifiesExistingEntity() {
        EventUpdateRequestDTO dto = TestDataFactory.eventUpdateRequestDto(TestDataFactory.uuid(), TestDataFactory.uuid());
        Place place = TestDataFactory.place(dto.placeId());
        Event event = TestDataFactory.event(dto.eventId(), place);
        when(placeRepository.findById(dto.placeId())).thenReturn(Optional.of(place));
        when(eventRepository.findById(dto.eventId())).thenReturn(Optional.of(event));

        String result = service.updateEvent(dto);

        assertEquals(EventResponseMessages.EVENT_UPDATE_MESSAGE, result);
        verify(eventRepository).save(event);
    }

    @Test
    void deleteEventRemovesEntity() {
        UUID eventId = TestDataFactory.uuid();
        Event event = TestDataFactory.event(eventId, TestDataFactory.place(TestDataFactory.uuid()));
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        String result = service.deleteEvent(eventId);

        assertEquals(EventResponseMessages.EVENT_DELETE_MESSAGE, result);
        verify(eventRepository).deleteById(eventId);
    }
}
