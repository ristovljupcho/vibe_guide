package com.vibe_guide.services.impl;

import com.vibe_guide.converters.UserConverter;
import com.vibe_guide.data.TestDataFactory;
import com.vibe_guide.dtos.PlaceAdminResponseDTO;
import com.vibe_guide.entities.User;
import com.vibe_guide.repositories.PlaceAdminRepository;
import com.vibe_guide.repositories.PlaceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlaceAdminQueryServiceImplTest {

    @Mock
    private PlaceAdminRepository placeAdminRepository;

    @Mock
    private PlaceRepository placeRepository;

    @Mock
    private UserConverter userConverter;

    @InjectMocks
    private PlaceAdminQueryServiceImpl service;

    @Test
    void getAllAdminsForPlaceReturnsMappedDtos() {
        var placeId = TestDataFactory.uuid();
        User user = TestDataFactory.user(TestDataFactory.uuid());
        PlaceAdminResponseDTO dto = TestDataFactory.placeAdminResponseDto(user.getId());
        when(placeRepository.existsById(placeId)).thenReturn(true);
        when(placeAdminRepository.findAllByPlaceId(placeId)).thenReturn(List.of(user));
        when(userConverter.toPlaceAdminResponseDTO(user)).thenReturn(dto);

        List<PlaceAdminResponseDTO> result = service.getAllAdminsForPlace(placeId);

        assertEquals(List.of(dto), result);
    }
}
