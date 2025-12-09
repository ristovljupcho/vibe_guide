package com.vibe_guide.services.impl;

import com.vibe_guide.data.TestDataFactory;
import com.vibe_guide.dtos.WorkingHoursDeleteRequestDTO;
import com.vibe_guide.dtos.WorkingHoursRequestDTO;
import com.vibe_guide.entities.Place;
import com.vibe_guide.entities.WorkingHours;
import com.vibe_guide.entities.composite_keys.WorkingHoursId;
import com.vibe_guide.enums.DayOfWeek;
import com.vibe_guide.repositories.PlaceRepository;
import com.vibe_guide.repositories.WorkingHoursRepository;
import com.vibe_guide.utils.WorkingHoursResponseMessages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkingHoursManagementServiceImplTest {

    @Mock
    private WorkingHoursRepository workingHoursRepository;

    @Mock
    private PlaceRepository placeRepository;

    @InjectMocks
    private WorkingHoursManagementServiceImpl service;

    @Test
    void insertWorkingHoursForPlacePersistsEntries() {
        UUID placeId = TestDataFactory.uuid();
        Place place = TestDataFactory.place(placeId);
        List<WorkingHoursRequestDTO> dtos = List.of(TestDataFactory.workingHoursRequestDto(DayOfWeek.MONDAY));
        when(placeRepository.findById(placeId)).thenReturn(Optional.of(place));
        when(workingHoursRepository.findAllByPlaceId(placeId)).thenReturn(List.of());

        String result = service.insertWorkingHoursForPlace(placeId, dtos);

        assertEquals(String.format(WorkingHoursResponseMessages.WORKING_HOURS_INSERT_MESSAGE, "MONDAY", placeId),
                result);
        verify(workingHoursRepository).saveAll(anyList());
    }

    @Test
    void updateDailyWorkingHoursForPlaceUpdatesExistingRecord() {
        UUID placeId = TestDataFactory.uuid();
        WorkingHours existing = TestDataFactory.workingHoursEntity(TestDataFactory.place(placeId), DayOfWeek.MONDAY);
        WorkingHoursRequestDTO dto = TestDataFactory.workingHoursRequestDto(DayOfWeek.MONDAY);
        when(placeRepository.existsById(placeId)).thenReturn(true);
        when(workingHoursRepository.findByPlaceIdAndDayOfWeek(placeId, DayOfWeek.MONDAY))
                .thenReturn(Optional.of(existing));

        String result = service.updateDailyWorkingHoursForPlace(placeId, dto);

        assertEquals(String.format(WorkingHoursResponseMessages.WORKING_HOURS_UPDATE_MESSAGE, DayOfWeek.MONDAY, placeId),
                result);
        verify(workingHoursRepository).save(existing);
    }

    @Test
    void deleteWorkingHoursForPlaceDeletesRequestedDays() {
        UUID placeId = TestDataFactory.uuid();
        WorkingHours monday = TestDataFactory.workingHoursEntity(TestDataFactory.place(placeId), DayOfWeek.MONDAY);
        WorkingHours tuesday = TestDataFactory.workingHoursEntity(TestDataFactory.place(placeId), DayOfWeek.TUESDAY);
        WorkingHoursDeleteRequestDTO dto = TestDataFactory.workingHoursDeleteRequestDto(List.of(DayOfWeek.MONDAY));
        when(placeRepository.existsById(placeId)).thenReturn(true);
        when(workingHoursRepository.findAllByPlaceId(placeId)).thenReturn(List.of(monday, tuesday));

        String result = service.deleteWorkingHoursForPlace(placeId, dto);

        assertEquals(String.format(WorkingHoursResponseMessages.WORKING_HOURS_DELETE_MESSAGE, "MONDAY", placeId),
                result);
        var expectedIds = dto.daysToDelete().stream()
                .map(day -> new WorkingHoursId(day, placeId))
                .toList();
        verify(workingHoursRepository).deleteAllById(expectedIds);
    }
}
