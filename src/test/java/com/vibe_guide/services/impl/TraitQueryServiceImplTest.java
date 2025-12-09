package com.vibe_guide.services.impl;

import com.vibe_guide.converters.TraitConverter;
import com.vibe_guide.data.TestDataFactory;
import com.vibe_guide.dtos.TraitCarouselResponseDTO;
import com.vibe_guide.dtos.TraitResponseDTO;
import com.vibe_guide.entities.Trait;
import com.vibe_guide.enums.TraitType;
import com.vibe_guide.enums.sorting.SortDirection;
import com.vibe_guide.enums.sorting.TraitSortBy;
import com.vibe_guide.repositories.TraitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TraitQueryServiceImplTest {

    @Mock
    private TraitRepository traitRepository;

    @Mock
    private TraitConverter traitConverter;

    @InjectMocks
    private TraitQueryServiceImpl service;

    @Test
    void getPaginatedTraitsWithoutFilterUsesFindAll() {
        Trait trait = TestDataFactory.trait(TestDataFactory.uuid());
        TraitResponseDTO dto = TestDataFactory.traitResponseDto(trait.getId());
        Page<Trait> page = new PageImpl<>(List.of(trait));
        when(traitRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(traitConverter.toTraitResponseDTO(trait)).thenReturn(dto);

        Page<TraitResponseDTO> result = service.getPaginatedTraits(null, TraitSortBy.DEFAULT,
                SortDirection.DESC, 0, 5);

        assertEquals(List.of(dto), result.getContent());
    }

    @Test
    void getPaginatedTraitsWithTypeFilterUsesRepositoryFilter() {
        Trait trait = TestDataFactory.trait(TestDataFactory.uuid());
        TraitResponseDTO dto = TestDataFactory.traitResponseDto(trait.getId());
        Page<Trait> page = new PageImpl<>(List.of(trait));
        when(traitRepository.findAllByTraitType(any(), any(Pageable.class))).thenReturn(page);
        when(traitConverter.toTraitResponseDTO(trait)).thenReturn(dto);

        Page<TraitResponseDTO> result = service.getPaginatedTraits(TraitType.FOOD, TraitSortBy.NAME,
                SortDirection.ASC, 0, 5);

        assertEquals(List.of(dto), result.getContent());
    }

    @Test
    void getAllTraitsReturnsCarouselDtos() {
        Trait trait = TestDataFactory.trait(TestDataFactory.uuid());
        TraitCarouselResponseDTO dto = TestDataFactory.traitCarouselResponseDto(trait.getName());
        when(traitRepository.findAll()).thenReturn(List.of(trait));
        when(traitConverter.toTraitCarouselResponseDTO(trait)).thenReturn(dto);

        List<TraitCarouselResponseDTO> result = service.getAllTraits();

        assertEquals(List.of(dto), result);
    }
}
