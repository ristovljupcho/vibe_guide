package com.vibe_guide.services.impl;

import com.vibe_guide.converters.TraitConverter;
import com.vibe_guide.dtos.TraitResponseDTO;
import com.vibe_guide.entities.Trait;
import com.vibe_guide.repositories.TraitRepository;
import com.vibe_guide.utils.SharedTestData;
import com.vibe_guide.utils.TraitTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TraitQueryServiceImplTest {
    @Mock
    TraitRepository traitRepository;

    @Mock
    TraitConverter traitConverter;

    @InjectMocks
    TraitQueryServiceImpl traitQueryService;

    @Test
    void getPaginatedTraits_atLeastOneTraitExists_returnPageOfTraitResponseDTO() {
        // given
        Page<TraitResponseDTO> traitResponseDTOs = TraitTestData.getPageTraitResponseDTOs();

        given(traitRepository.findAll(TraitTestData.TRAIT_PAGE_REQUEST)).willReturn(TraitTestData.getPageTraits());
        given(traitConverter.toTraitResponseDTO((Trait) any())).willReturn(traitResponseDTOs.getContent().get(0),
                traitResponseDTOs.getContent().get(1), traitResponseDTOs.getContent().get(2));

        // when
        Page<TraitResponseDTO> actualResult = traitQueryService.getPaginatedTraits(
                null,
                TraitTestData.TRAIT_SORT_BY,
                SharedTestData.SORT_DIRECTION,
                SharedTestData.PAGE,
                SharedTestData.SIZE
        );

        // then
        assertThat(actualResult).isEqualTo(TraitTestData.getPageTraitResponseDTOs());
    }

    @Test
    void getPaginatedTraits_atLeastOneTraitExistsAndFilterByTraitType_returnPageOfTraitResponseDTO() {
        // given
        Page<TraitResponseDTO> traitResponseDTOs = TraitTestData.getPageTraitResponseDTOs();

        given(traitRepository.findAllByTraitType(
                TraitTestData.TRAIT_TYPE,
                TraitTestData.TRAIT_PAGE_REQUEST)
        ).willReturn(TraitTestData.getPageTraits());
        given(traitConverter.toTraitResponseDTO((Trait) any())).willReturn(traitResponseDTOs.getContent().get(0),
                traitResponseDTOs.getContent().get(1), traitResponseDTOs.getContent().get(2));

        // when
        Page<TraitResponseDTO> actualResult = traitQueryService.getPaginatedTraits(
                TraitTestData.TRAIT_TYPE,
                TraitTestData.TRAIT_SORT_BY,
                SharedTestData.SORT_DIRECTION,
                SharedTestData.PAGE,
                SharedTestData.SIZE
        );

        // then
        assertThat(actualResult).isEqualTo(traitResponseDTOs);
    }
}
