package com.vibe_guide.services.impl;

import com.vibe_guide.converters.EventConverter;
import com.vibe_guide.repositories.EventRepository;
import com.vibe_guide.repositories.PlaceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EventQueryServiceImplTest {
    @Mock
    EventRepository eventRepository;

    @Mock
    PlaceRepository placeRepository;

    @Mock
    EventConverter eventConverter;

    @Test
    void findByCriteria_() {

    }
}
