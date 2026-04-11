package com.vibe_guide.event.services;

import com.vibe_guide.event.dtos.EventInsertRequestDTO;
import com.vibe_guide.event.dtos.EventUpdateRequestDTO;
import java.util.UUID;

public interface EventManagementService {
  String insert(EventInsertRequestDTO eventInsertRequestDTO);

  String update(EventUpdateRequestDTO eventUpdateRequestDTO);

  String delete(UUID eventId);
}
