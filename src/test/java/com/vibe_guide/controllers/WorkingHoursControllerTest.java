package com.vibe_guide.controllers;

import com.vibe_guide.data.TestDataFactory;
import com.vibe_guide.dtos.WorkingHoursDeleteRequestDTO;
import com.vibe_guide.dtos.WorkingHoursRequestDTO;
import com.vibe_guide.dtos.WorkingHoursResponseDTO;
import com.vibe_guide.enums.DayOfWeek;
import com.vibe_guide.services.WorkingHoursManagementService;
import com.vibe_guide.services.WorkingHoursQueryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkingHoursControllerTest {

    @Mock
    private WorkingHoursQueryService workingHoursQueryService;

    @Mock
    private WorkingHoursManagementService workingHoursManagementService;

    @InjectMocks
    private WorkingHoursController controller;

    @Test
    void getWeeklyWorkingHoursReturnsDtos() {
        UUID placeId = TestDataFactory.uuid();
        List<WorkingHoursResponseDTO> expected = List.of(TestDataFactory.workingHoursResponseDto(DayOfWeek.MONDAY));
        when(workingHoursQueryService.getWeeklyWorkingHoursForPlace(placeId)).thenReturn(expected);

        ResponseEntity<List<WorkingHoursResponseDTO>> response = controller.getWeeklyWorkingHoursForPlace(placeId);

        assertEquals(expected, response.getBody());
    }

    @Test
    void insertWorkingHoursReturnsMessage() {
        UUID placeId = TestDataFactory.uuid();
        List<WorkingHoursRequestDTO> requests = List.of(TestDataFactory.workingHoursRequestDto(DayOfWeek.MONDAY));
        when(workingHoursManagementService.insertWorkingHoursForPlace(placeId, requests)).thenReturn("created");

        ResponseEntity<String> response = controller.insertWorkingHoursForPlace(placeId, requests);

        assertEquals("created", response.getBody());
    }

    @Test
    void deleteWorkingHoursReturnsMessage() {
        UUID placeId = TestDataFactory.uuid();
        WorkingHoursDeleteRequestDTO dto = TestDataFactory.workingHoursDeleteRequestDto(List.of(DayOfWeek.MONDAY));
        when(workingHoursManagementService.deleteWorkingHoursForPlace(placeId, dto)).thenReturn("deleted");

        ResponseEntity<String> response = controller.updateDailyWorkingHoursForPlace(placeId, dto);

        assertEquals("deleted", response.getBody());
    }
}
