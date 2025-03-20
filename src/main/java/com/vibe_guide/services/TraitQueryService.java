package com.vibe_guide.services;

import com.vibe_guide.dtos.TraitResponseDTO;
import com.vibe_guide.enums.sorting.SortDirection;
import com.vibe_guide.enums.sorting.TraitSortBy;
import com.vibe_guide.enums.TraitType;
import org.springframework.data.domain.Page;

public interface TraitQueryService {

    Page<TraitResponseDTO> getPaginatedTraits(TraitType traitType, TraitSortBy sortBy,
                                              SortDirection sortDirection,
                                              int page,
                                              int size);
}