package com.vibe_guide.domain.event.services;

import com.vibe_guide.domain.event.dtos.EventInsertRequestDTO;
import com.vibe_guide.domain.event.dtos.EventUpdateRequestDTO;
import java.util.UUID;

public interface EventManagementService {
  String insert(EventInsertRequestDTO eventInsertRequestDTO);

  String update(EventUpdateRequestDTO eventUpdateRequestDTO);

  String delete(UUID eventId);
}

