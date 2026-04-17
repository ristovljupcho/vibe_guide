package com.vibe_guide.domain.trait.services;

import com.vibe_guide.enums.TraitType;
import com.vibe_guide.enums.sorting.SortDirection;
import com.vibe_guide.enums.sorting.TraitSortBy;
import com.vibe_guide.domain.trait.dtos.TraitCarouselResponseDTO;
import com.vibe_guide.domain.trait.dtos.TraitResponseDTO;
import java.util.List;
import org.springframework.data.domain.Page;

public interface TraitQueryService {

  Page<TraitResponseDTO> getPaginated(
      TraitType traitType, TraitSortBy sortBy, SortDirection sortDirection, int page, int size);

  List<TraitCarouselResponseDTO> getAll();
}

