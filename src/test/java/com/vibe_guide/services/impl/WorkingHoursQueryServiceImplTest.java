package com.vibe_guide.services.impl;

import com.vibe_guide.converters.WorkingHoursConverter;
import com.vibe_guide.data.TestDataFactory;
import com.vibe_guide.dtos.WorkingHoursMissingDaysResponseDTO;
import com.vibe_guide.dtos.WorkingHoursResponseDTO;
import com.vibe_guide.entities.Place;
import com.vibe_guide.entities.WorkingHours;
import com.vibe_guide.enums.DayOfWeek;
import com.vibe_guide.repositories.PlaceRepository;
import com.vibe_guide.repositories.WorkingHoursRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkingHoursQueryServiceImplTest {

    @Mock
    private WorkingHoursRepository workingHoursRepository;

    @Mock
    private PlaceRepository placeRepository;

    @Mock
    private WorkingHoursConverter workingHoursConverter;

    @InjectMocks
    private WorkingHoursQueryServiceImpl service;

    @Test
    void getWeeklyWorkingHoursForPlaceMapsEntities() {
        UUID placeId = TestDataFactory.uuid();
        Place place = TestDataFactory.place(placeId);
        WorkingHours entity = TestDataFactory.workingHoursEntity(place, DayOfWeek.MONDAY);
        WorkingHoursResponseDTO dto = TestDataFactory.workingHoursResponseDto(DayOfWeek.MONDAY);
        when(placeRepository.existsById(placeId)).thenReturn(true);
        when(workingHoursRepository.findAllByPlaceId(placeId)).thenReturn(List.of(entity));
        when(workingHoursConverter.toWorkingHoursResponseDTO(entity)).thenReturn(dto);

        List<WorkingHoursResponseDTO> result = service.getWeeklyWorkingHoursForPlace(placeId);

        assertEquals(List.of(dto), result);
    }

    @Test
    void getMissingWorkingHoursDaysForPlaceReturnsMissingDays() {
        UUID placeId = TestDataFactory.uuid();
        Place place = TestDataFactory.place(placeId);
        WorkingHours entity = TestDataFactory.workingHoursEntity(place, DayOfWeek.MONDAY);
        WorkingHoursMissingDaysResponseDTO dto = TestDataFactory.workingHoursMissingDayDto(DayOfWeek.TUESDAY);
        when(placeRepository.existsById(placeId)).thenReturn(true);
        when(workingHoursRepository.findAllByPlaceId(placeId)).thenReturn(List.of(entity));
        when(workingHoursConverter.toWorkingHoursMissingDaysResponseDTO(DayOfWeek.TUESDAY)).thenReturn(dto);

        List<WorkingHoursMissingDaysResponseDTO> result = service.getMissingWorkingHoursDaysForPlace(placeId);

        assertEquals(List.of(dto), result);
    }
}
