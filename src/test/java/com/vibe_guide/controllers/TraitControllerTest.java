package com.vibe_guide.controllers;

import com.vibe_guide.data.TestDataFactory;
import com.vibe_guide.dtos.TraitInsertRequestDTO;
import com.vibe_guide.dtos.TraitResponseDTO;
import com.vibe_guide.enums.TraitType;
import com.vibe_guide.enums.sorting.SortDirection;
import com.vibe_guide.enums.sorting.TraitSortBy;
import com.vibe_guide.services.TraitManagementService;
import com.vibe_guide.services.TraitQueryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TraitControllerTest {

    @Mock
    private TraitQueryService traitQueryService;

    @Mock
    private TraitManagementService traitManagementService;

    @InjectMocks
    private TraitController controller;

    @Test
    void getPaginatedTraitsReturnsPage() {
        Page<TraitResponseDTO> page = new PageImpl<>(List.of(TestDataFactory.traitResponseDto(TestDataFactory.uuid())));
        when(traitQueryService.getPaginatedTraits(TraitType.FOOD, TraitSortBy.DEFAULT, SortDirection.ASC, 0, 5))
                .thenReturn(page);

        ResponseEntity<Page<TraitResponseDTO>> response =
                controller.getPaginatedTraits(TraitType.FOOD, TraitSortBy.DEFAULT, SortDirection.ASC, 0, 5);

        assertEquals(page, response.getBody());
    }

    @Test
    void insertTraitReturnsMessage() {
        TraitInsertRequestDTO dto = TestDataFactory.traitInsertRequestDto();
        when(traitManagementService.insertTrait(dto)).thenReturn("created");

        ResponseEntity<String> response = controller.insertTrait(dto);

        assertEquals("created", response.getBody());
    }

    @Test
    void deleteTraitReturnsMessage() {
        UUID traitId = TestDataFactory.uuid();
        when(traitManagementService.deleteTrait(traitId)).thenReturn("deleted");

        ResponseEntity<String> response = controller.deleteTrait(traitId);

        assertEquals("deleted", response.getBody());
    }
}
