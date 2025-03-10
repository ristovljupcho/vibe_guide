package com.vibe_guide.services;


import com.vibe_guide.dtos.EventInsertRequestDTO;
import com.vibe_guide.dtos.EventUpdateRequestDTO;

import java.util.UUID;

public interface EventManagementService {
    String insertEvent(EventInsertRequestDTO eventInsertRequestDTO);
    String updateEvent(EventUpdateRequestDTO eventUpdateRequestDTO);
    String deleteEvent(UUID eventId);
}
