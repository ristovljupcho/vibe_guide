package com.vibe_guide.utils;

import com.vibe_guide.entities.Event;
import com.vibe_guide.entities.EventGallery;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@UtilityClass
public class EventTestData {
    public static final UUID EVENT_ID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
    private static final String EVENT_NAME = "Daily Offer Name 1";
    private static final String DAILY_OFFER_DESCRIPTION = "Description 1";
    private static final LocalDateTime START_DATE = LocalDateTime.now().plusDays(1);
    private static final LocalDateTime END_DATE = LocalDateTime.now().plusDays(2);
    private static final Set<EventGallery> EVENT_GALLERY_SET = new HashSet<>();

    public List<Event> getEvents() {
        Event event1 = new Event();
        event1.setId(EVENT_ID);
        event1.setName(EVENT_NAME);
        event1.setDescription(DAILY_OFFER_DESCRIPTION);
        event1.setStartDate(START_DATE);
        event1.setEndDate(END_DATE);
        event1.setPlace(PlaceTestData.getPlace());
        event1.setGalleries(EVENT_GALLERY_SET);

        Event event2 = new Event();
        event2.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174001"));
        event2.setName(EVENT_NAME);
        event2.setDescription(DAILY_OFFER_DESCRIPTION);
        event2.setStartDate(START_DATE);
        event2.setEndDate(END_DATE);
        event2.setPlace(PlaceTestData.getPlace());
        event2.setGalleries(EVENT_GALLERY_SET);

        Event event3 = new Event();
        event3.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174001"));
        event3.setName(EVENT_NAME);
        event3.setDescription(DAILY_OFFER_DESCRIPTION);
        event3.setStartDate(START_DATE);
        event3.setEndDate(END_DATE);
        event3.setPlace(PlaceTestData.getPlace());
        event3.setGalleries(EVENT_GALLERY_SET);

        return List.of(event1, event2, event3);
    }

    public Event getEvent() {
        return getEvents().getFirst();
    }
}
