package com.vibe_guide.services;

import com.vibe_guide.dtos.TraitCarouselResponseDTO;
import com.vibe_guide.dtos.TraitResponseDTO;
import com.vibe_guide.enums.TraitType;
import com.vibe_guide.enums.sorting.SortDirection;
import com.vibe_guide.enums.sorting.TraitSortBy;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TraitQueryService {

    Page<TraitResponseDTO> getPaginatedTraits(TraitType traitType, TraitSortBy sortBy,
                                              SortDirection sortDirection,
                                              int page,
                                              int size);

    List<TraitCarouselResponseDTO> getAllTraits();
}