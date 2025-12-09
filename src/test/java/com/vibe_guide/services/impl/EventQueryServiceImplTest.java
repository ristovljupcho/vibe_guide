package com.vibe_guide.services.impl;

import com.vibe_guide.converters.EventConverter;
import com.vibe_guide.data.TestDataFactory;
import com.vibe_guide.dtos.EventResponseDTO;
import com.vibe_guide.dtos.EventSearchCriteriaDTO;
import com.vibe_guide.entities.Event;
import com.vibe_guide.entities.Place;
import com.vibe_guide.repositories.EventRepository;
import com.vibe_guide.repositories.PlaceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventQueryServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private PlaceRepository placeRepository;

    @Mock
    private EventConverter eventConverter;

    @InjectMocks
    private EventQueryServiceImpl service;

    @Test
    void getPaginatedEventsBuildsSpecification() {
        Event event = TestDataFactory.event(TestDataFactory.uuid(), TestDataFactory.place(TestDataFactory.uuid()));
        EventResponseDTO dto = TestDataFactory.eventResponseDto();
        Page<Event> page = new PageImpl<>(List.of(event));
        EventSearchCriteriaDTO criteria = TestDataFactory.eventSearchCriteriaDto();
        when(eventRepository.findAll(any(), any(Pageable.class))).thenReturn(page);
        when(eventConverter.toEventResponseDTO(event)).thenReturn(dto);

        Page<EventResponseDTO> result = service.getPaginatedEvents(criteria, 0, 5);

        assertEquals(List.of(dto), result.getContent());
    }

    @Test
    void findPastEventsByPlaceIdValidatesPlace() {
        UUID placeId = TestDataFactory.uuid();
        Place place = TestDataFactory.place(placeId);
        Event event = TestDataFactory.event(TestDataFactory.uuid(), place);
        EventResponseDTO dto = TestDataFactory.eventResponseDto();
        when(placeRepository.findById(placeId)).thenReturn(Optional.of(place));
        when(eventRepository.findPastEvents(any(), any(), any())).thenReturn(List.of(event));
        when(eventConverter.toEventResponseDTO(event)).thenReturn(dto);

        List<EventResponseDTO> result = service.findPastEventsByPlaceId(placeId);

        assertEquals(List.of(dto), result);
    }

    @Test
    void findActiveEventsByPlaceIdValidatesPlace() {
        UUID placeId = TestDataFactory.uuid();
        Place place = TestDataFactory.place(placeId);
        Event event = TestDataFactory.event(TestDataFactory.uuid(), place);
        EventResponseDTO dto = TestDataFactory.eventResponseDto();
        when(placeRepository.findById(placeId)).thenReturn(Optional.of(place));
        when(eventRepository.findActiveEventsByPlaceId(any(), any())).thenReturn(List.of(event));
        when(eventConverter.toEventResponseDTO(event)).thenReturn(dto);

        List<EventResponseDTO> result = service.findActiveEventsByPlaceId(placeId);

        assertEquals(List.of(dto), result);
    }
}
