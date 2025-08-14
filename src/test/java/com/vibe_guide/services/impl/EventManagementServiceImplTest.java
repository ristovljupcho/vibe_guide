package com.vibe_guide.services.impl;

import com.vibe_guide.dtos.EventInsertRequestDTO;
import com.vibe_guide.dtos.EventUpdateRequestDTO;
import com.vibe_guide.exceptions.EventNotFoundException;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.repositories.EventRepository;
import com.vibe_guide.repositories.PlaceRepository;
import com.vibe_guide.utils.EventTestData;
import com.vibe_guide.utils.PlaceTestData;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EventManagementServiceImplTest {
    @Mock
    EventRepository eventRepository;

    @Mock
    PlaceRepository placeRepository;

    @Mock
    EventGalleryManagementServiceImpl eventGalleryManagementService;

    @InjectMocks
    EventManagementServiceImpl eventManagementService;

    @SneakyThrows
    @Test
    void insertEvent_placeNotFound_throwsPlaceNotFoundException() {
        // given
        UUID placeId = PlaceTestData.PLACE_ID;
        EventInsertRequestDTO dto = EventTestData.getEventInsertRequestDTO();
        given(placeRepository.findById(any())).willReturn(Optional.empty());

        // when & then
        assertThatExceptionOfType(PlaceNotFoundException.class)
                .isThrownBy(() -> eventManagementService.insertEvent(dto))
                .withMessage("Place with id " + placeId + " not found.");
        verify(eventRepository, times(0)).save(any());
        verify(eventGalleryManagementService, times(0)).addImagesToEvent(any(), any());
    }

    @Test
    void insertEvent_eventInsertedSuccessfully_returnsMessage() {
        // given
        EventInsertRequestDTO dto = EventTestData.getEventInsertRequestDTO();
        given(placeRepository.findById(any())).willReturn(Optional.of(PlaceTestData.getPlace()));
        given(eventRepository.save(any())).willReturn(EventTestData.getEvent());

        // when
        String actualResult = eventManagementService.insertEvent(dto);

        // then
        assertThat(actualResult).isEqualTo("Event successfully inserted.");
        verify(eventRepository, times(1)).save(any());
    }

    @SneakyThrows
    @Test
    void updateEvent_placeNotFound_throwsPlaceNotFoundException() {
        // given
        UUID placeId = PlaceTestData.PLACE_ID;
        EventUpdateRequestDTO dto = EventTestData.getEventUpdateRequestDTO();
        given(placeRepository.findById(any())).willReturn(Optional.empty());

        // when & then
        assertThatExceptionOfType(PlaceNotFoundException.class)
                .isThrownBy(() -> eventManagementService.updateEvent(dto))
                .withMessage("Place with id " + placeId + " not found.");
        verify(eventRepository, times(0)).save(any());
    }

    @Test
    void updateEvent_eventUpdatedSuccessfully_returnsMessage() {
        // given
        EventUpdateRequestDTO dto = EventTestData.getEventUpdateRequestDTO();
        given(placeRepository.findById(any())).willReturn(Optional.of(PlaceTestData.getPlace()));
        given(eventRepository.save(any())).willReturn(EventTestData.getEvent());
        given(eventRepository.findById(dto.eventId())).willReturn(Optional.of(EventTestData.getEvent()));

        // when
        String actualResult = eventManagementService.updateEvent(dto);

        // then
        assertThat(actualResult).isEqualTo("Event successfully updated.");
        verify(eventRepository, times(1)).save(any());
    }

    @SneakyThrows
    @Test
    void deleteEvent_eventIdNotFound_throwsEventIdNotFoundException() {
        // given
        UUID eventId = EventTestData.EVENT_ID;
        given(eventRepository.findById(any())).willReturn(Optional.empty());

        // when & then
        assertThatExceptionOfType(EventNotFoundException.class)
                .isThrownBy(() -> eventManagementService.deleteEvent(eventId))
                .withMessage("Event with id '" + eventId + "' not found.");
        verify(eventRepository, times(0)).deleteById(any());
    }

    @Test
    void deleteEvent_eventDeletedSuccessfully_returnsMessage() {
        // given
        UUID eventId = EventTestData.EVENT_ID;
        given(eventRepository.findById(any())).willReturn(Optional.of(EventTestData.getEvent()));

        // when
        String actualResult = eventManagementService.deleteEvent(eventId);

        //then
        assertThat(actualResult).isEqualTo("Event successfully deleted.");
    }
}