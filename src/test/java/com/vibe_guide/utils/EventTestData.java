package com.vibe_guide.utils;

import com.vibe_guide.dtos.EventInsertRequestDTO;
import com.vibe_guide.dtos.EventResponseDTO;
import com.vibe_guide.dtos.EventSearchCriteriaDTO;
import com.vibe_guide.dtos.EventUpdateRequestDTO;
import com.vibe_guide.entities.Event;
import com.vibe_guide.entities.EventGallery;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@UtilityClass
public class EventTestData {
    public static final UUID EVENT_ID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
    private static final String EVENT_NAME = "Event Name 1";
    private static final String EVENT_DESCRIPTION = "Description 1";
    private static final LocalDateTime EVENT_START_DATE = LocalDateTime.now().plusDays(1);
    private static final LocalDateTime EVENT_END_DATE = LocalDateTime.now().plusDays(2);
    private static final Set<EventGallery> EVENT_GALLERY_SET = new HashSet<>();

    MultipartFile mockFile1 = new MockMultipartFile("image1", "image1.jpg", "image/jpeg", new byte[0]);
    MultipartFile mockFile2 = new MockMultipartFile("image2", "image2.jpg", "image/jpeg", new byte[0]);

    public List<Event> getEvents() {
        Event event1 = new Event();
        event1.setId(EVENT_ID);
        event1.setName(EVENT_NAME);
        event1.setDescription(EVENT_DESCRIPTION);
        event1.setStartDate(EVENT_START_DATE);
        event1.setEndDate(EVENT_END_DATE);
        event1.setPlace(PlaceTestData.getPlace());
        event1.setGalleries(EVENT_GALLERY_SET);

        Event event2 = new Event();
        event2.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174001"));
        event2.setName(EVENT_NAME);
        event2.setDescription(EVENT_DESCRIPTION);
        event2.setStartDate(EVENT_START_DATE);
        event2.setEndDate(EVENT_END_DATE);
        event2.setPlace(PlaceTestData.getPlace());
        event2.setGalleries(EVENT_GALLERY_SET);

        Event event3 = new Event();
        event3.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174001"));
        event3.setName(EVENT_NAME);
        event3.setDescription(EVENT_DESCRIPTION);
        event3.setStartDate(EVENT_START_DATE);
        event3.setEndDate(EVENT_END_DATE);
        event3.setPlace(PlaceTestData.getPlace());
        event3.setGalleries(EVENT_GALLERY_SET);

        return List.of(event1, event2, event3);
    }

    public Event getEvent() {
        return getEvents().getFirst();
    }

    public static Page<Event> getPageOfEvents() {
        return new PageImpl<>(EventTestData.getEvents());
    }

    public static List<EventResponseDTO> getEventResponseDTOs() {

        List<String> sampleImages = List.of("img1.jpg", "img2.jpg");
        EventResponseDTO dto1 = new EventResponseDTO(EVENT_NAME, PlaceTestData.PLACE_NAME, EVENT_DESCRIPTION,
                EVENT_START_DATE, EVENT_END_DATE, sampleImages);
        EventResponseDTO dto2 = new EventResponseDTO(EVENT_NAME, PlaceTestData.PLACE_NAME, EVENT_DESCRIPTION,
                EVENT_START_DATE, EVENT_END_DATE, sampleImages);
        EventResponseDTO dto3 = new EventResponseDTO(EVENT_NAME, PlaceTestData.PLACE_NAME, EVENT_DESCRIPTION,
                EVENT_START_DATE, EVENT_END_DATE, sampleImages);

        return List.of(dto1, dto2, dto3);
    }

    public static Page<EventResponseDTO> getEventResponseDTOsPage() {
        return new PageImpl<>(EventTestData.getEventResponseDTOs());
    }

    public static EventSearchCriteriaDTO getEventsSearchCriteriaDTO() {
        return new EventSearchCriteriaDTO(EVENT_NAME, EVENT_START_DATE, EVENT_END_DATE);
    }

    public List<EventInsertRequestDTO> getEventInsertRequestDTOs() {
        List<MultipartFile> sampleImages = List.of(mockFile1, mockFile2);

        EventInsertRequestDTO dto1 = new EventInsertRequestDTO(
                EVENT_NAME,
                EVENT_DESCRIPTION,
                EVENT_START_DATE,
                EVENT_END_DATE,
                PlaceTestData.PLACE_ID,
                sampleImages
        );

        EventInsertRequestDTO dto2 = new EventInsertRequestDTO(
                EVENT_NAME,
                EVENT_DESCRIPTION,
                EVENT_START_DATE,
                EVENT_END_DATE,
                PlaceTestData.PLACE_ID,
                sampleImages
        );
        EventInsertRequestDTO dto3 = new EventInsertRequestDTO(
                EVENT_NAME,
                EVENT_DESCRIPTION,
                EVENT_START_DATE,
                EVENT_END_DATE,
                PlaceTestData.PLACE_ID,
                sampleImages
        );
        return List.of(dto1, dto2, dto3);
    }

    public EventInsertRequestDTO getEventInsertRequestDTO() {
        return getEventInsertRequestDTOs().getFirst();
    }

    public List<EventUpdateRequestDTO> getEventUpdateDTOs() {

        EventUpdateRequestDTO dto1 = new EventUpdateRequestDTO(
                UUID.fromString("123e4567-e89b-12d3-a456-426614174001"),
                EVENT_NAME,
                EVENT_DESCRIPTION,
                EVENT_START_DATE,
                EVENT_END_DATE,
                PlaceTestData.PLACE_ID
        );
        return List.of(dto1);
    }

    public EventUpdateRequestDTO getEventUpdateRequestDTO() {
        return getEventUpdateDTOs().getFirst();
    }
}