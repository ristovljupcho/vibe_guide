package com.vibe_guide.services.impl;

import com.vibe_guide.converters.EventConverter;
import com.vibe_guide.dtos.EventResponseDTO;
import com.vibe_guide.entities.Event;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.repositories.EventRepository;
import com.vibe_guide.repositories.PlaceRepository;
import com.vibe_guide.utils.EventTestData;
import com.vibe_guide.utils.PlaceTestData;
import com.vibe_guide.utils.SharedTestData;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EventQueryServiceImplTest {
    @Mock
    EventRepository eventRepository;

    @Mock
    PlaceRepository placeRepository;

    @Mock
    EventConverter eventConverter;

    @InjectMocks
    EventQueryServiceImpl eventQueryService;

    @Test
    void getPaginatedEvents__returnsPageOfEventResponseDTO() {
        // given
        List<EventResponseDTO> dtos = EventTestData.getEventResponseDTOs();
        given(eventRepository.findAll((Specification<Event>) any(), (Pageable) any())).willReturn(
                EventTestData.getPageOfEvents());
        given(eventConverter.toEventResponseDTO(any())).willReturn(dtos.get(0), dtos.get(1), dtos.get(2));

        // when
        Page<EventResponseDTO> actualResult =
                eventQueryService.getPaginatedEvents(EventTestData.getEventsSearchCriteriaDTO(), SharedTestData.PAGE,
                        SharedTestData.SIZE);

        // then
        assertThat(actualResult).isEqualTo(EventTestData.getEventResponseDTOsPage());
    }

    @SneakyThrows
    @Test
    void findPastEventsByPlaceId_placeNotFound_throwsPlaceNotFoundException() {
        // given
        given(placeRepository.findById(any())).willReturn(Optional.empty());

        // when & then
        assertThatExceptionOfType(PlaceNotFoundException.class)
                .isThrownBy(() -> eventQueryService.findPastEventsByPlaceId(PlaceTestData.PLACE_ID))
                .withMessage("Place with id " + PlaceTestData.PLACE_ID + " not found.");
        verify(eventRepository, times(0)).findPastEvents(any(), any(), any());
        verify(eventConverter, times(0)).toEventResponseDTO(any());
    }

    @Test
    void findPastEventsByPlaceId_successfullyFoundProvidedPlaceId_returnsEventResponseDTOs() {
        // given
        List<EventResponseDTO> dtos = EventTestData.getEventResponseDTOs();
        given(placeRepository.findById(any())).willReturn(Optional.of(PlaceTestData.getPlace()));
        given(eventRepository.findPastEvents(any(), any(), any()))
                .willReturn(EventTestData.getEvents());
        given(eventConverter.toEventResponseDTO(any())).willReturn(dtos.get(0), dtos.get(1), dtos.get(2));

        // when
        List<EventResponseDTO> actualResult = eventQueryService.findPastEventsByPlaceId(PlaceTestData.PLACE_ID);

        // then
        assertThat(actualResult).isEqualTo(dtos);
    }

    @SneakyThrows
    @Test
    void findUpcomingEvents_placeNotFound_throwsPlaceNotFoundException() {
        // given
        given(placeRepository.findById(any())).willReturn(Optional.empty());

        // when & then
        assertThatExceptionOfType(PlaceNotFoundException.class)
                .isThrownBy(() -> eventQueryService.findUpcomingEventsByPlaceId(PlaceTestData.PLACE_ID))
                .withMessage("Place with id " + PlaceTestData.PLACE_ID + " not found.");
        verify(eventRepository, times(0)).findPastEvents(any(), any(), any());
        verify(eventConverter, times(0)).toEventResponseDTO(any());
    }

    @Test
    void findUpcomingEventsByPlaceId_successfullyFoundProvidedPlaceId_returnsEventResponseDTOs() {
        // given
        List<EventResponseDTO> dtos = EventTestData.getEventResponseDTOs();
        given(placeRepository.findById(PlaceTestData.PLACE_ID)).willReturn(Optional.of(PlaceTestData.getPlace()));
        given(eventRepository.findEventsByPlaceId(PlaceTestData.PLACE_ID))
                .willReturn(EventTestData.getEvents());
        given(eventConverter.toEventResponseDTO(any())).willReturn(dtos.get(0), dtos.get(1), dtos.get(2));

        // when
        List<EventResponseDTO> actualResult = eventQueryService.findUpcomingEventsByPlaceId(PlaceTestData.PLACE_ID);

        // then
        assertThat(actualResult).isEqualTo(dtos);
    }

    @SneakyThrows
    @Test
    void findTodaysEventsByPlaceId_placeNotFound_throwsPlaceNotFoundException() {
        // given
        given(placeRepository.findById(any())).willReturn(Optional.empty());

        // when & then
        assertThatExceptionOfType(PlaceNotFoundException.class)
                .isThrownBy(() -> eventQueryService.findTodaysEventsByPlaceId(PlaceTestData.PLACE_ID))
                .withMessage("Place with id " + PlaceTestData.PLACE_ID + " not found.");
        verify(eventRepository, times(0)).findTodayEventsByPlaceId(any(), any());
        verify(eventConverter, times(0)).toEventResponseDTO(any());
    }

    @Test
    void findTodaysEventsByPlaceId_successfullyFoundProvidedPlaceId_returnsEventResponseDTOs() {
        // given
        List<EventResponseDTO> dtos = EventTestData.getEventResponseDTOs();
        given(placeRepository.findById(PlaceTestData.PLACE_ID)).willReturn(Optional.of(PlaceTestData.getPlace()));
        given(eventRepository.findTodayEventsByPlaceId(any(), any()))
                .willReturn(EventTestData.getEvents());
        given(eventConverter.toEventResponseDTO(any())).willReturn(dtos.get(0), dtos.get(1), dtos.get(2));

        // when
        List<EventResponseDTO> actualResult = eventQueryService.findTodaysEventsByPlaceId(PlaceTestData.PLACE_ID);

        //then
        assertThat(actualResult).isEqualTo(dtos);
    }

    @Test
    void findUpcomingEvents_successfullyFoundEvents_returnsEventResponseDTOs() {
        // given
        List<EventResponseDTO> dtos = EventTestData.getEventResponseDTOs();
        given(eventRepository.findUpcomingEvents(any())).willReturn(EventTestData.getEvents());
        given(eventConverter.toEventResponseDTO(any())).willReturn(dtos.get(0), dtos.get(1), dtos.get(2));

        // when
        List<EventResponseDTO> actualResult = eventQueryService.findUpcomingEvents();

        // then
        assertThat(actualResult).isEqualTo(dtos);
    }
}