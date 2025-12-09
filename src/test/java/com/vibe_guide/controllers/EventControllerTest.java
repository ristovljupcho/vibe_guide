package com.vibe_guide.controllers;

import com.vibe_guide.data.TestDataFactory;
import com.vibe_guide.dtos.EventInsertRequestDTO;
import com.vibe_guide.dtos.EventResponseDTO;
import com.vibe_guide.services.EventManagementService;
import com.vibe_guide.services.EventQueryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventControllerTest {

    @Mock
    private EventQueryService eventQueryService;

    @Mock
    private EventManagementService eventManagementService;

    @InjectMocks
    private EventController controller;

    @Test
    void getPaginatedEventsReturnsPage() {
        Page<EventResponseDTO> page = new PageImpl<>(List.of(TestDataFactory.eventResponseDto()));
        when(eventQueryService.getPaginatedEvents(any(), eq(0), eq(10))).thenReturn(page);

        ResponseEntity<Page<EventResponseDTO>> response =
                controller.getPaginatedEvents("place", LocalDateTime.now(), LocalDateTime.now().plusDays(1), 0, 10);

        assertEquals(page, response.getBody());
    }

    @Test
    void insertEventReturnsMessage() {
        EventInsertRequestDTO dto = TestDataFactory.eventInsertRequestDto(TestDataFactory.uuid());
        when(eventManagementService.insertEvent(dto)).thenReturn("created");

        ResponseEntity<String> response = controller.insertEvent(dto);

        assertEquals("created", response.getBody());
    }

    @Test
    void deleteEventReturnsMessage() {
        UUID eventId = TestDataFactory.uuid();
        when(eventManagementService.deleteEvent(eventId)).thenReturn("deleted");

        ResponseEntity<String> response = controller.deleteEvent(eventId);

        assertEquals("deleted", response.getBody());
    }
}
