package com.vibe_guide.services.impl;

import com.vibe_guide.converters.EventConverter;
import com.vibe_guide.dtos.EventResponseDTO;
import com.vibe_guide.entities.Event;
import com.vibe_guide.repositories.EventRepository;
import com.vibe_guide.utils.EventTestData;
import com.vibe_guide.utils.SharedTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class EventQueryServiceImplTest {
    @Mock
    EventRepository eventRepository;

    @Mock
    EventConverter eventConverter;

    @InjectMocks
    EventQueryServiceImpl eventQueryService;

    @Test
    void findByCriteria__returnsPageOfEventResponseDTO() {
        // given
        List<EventResponseDTO> dtos = EventTestData.getEventResponseDTOs();
        given(eventRepository.findAll((Specification<Event>) any(), (Pageable) any())).willReturn(
                EventTestData.getPageOfEvents());
        given(eventConverter.toEventResponseDTO(any())).willReturn(dtos.get(0), dtos.get(1), dtos.get(2));

        // when
        Page<EventResponseDTO> actualResult =
                eventQueryService.findByCriteria(EventTestData.getEventsSearchCriteriaDTO(), SharedTestData.PAGE,
                        SharedTestData.SIZE);

        // then
        assertThat(actualResult).isEqualTo(EventTestData.getEventResponseDTOsPage());
    }
}
