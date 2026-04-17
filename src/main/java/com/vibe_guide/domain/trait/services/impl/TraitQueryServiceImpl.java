package com.vibe_guide.domain.trait.services.impl;

import com.vibe_guide.domain.trait.services.TraitQueryService;
import com.vibe_guide.enums.TraitType;
import com.vibe_guide.enums.sorting.SortDirection;
import com.vibe_guide.enums.sorting.TraitSortBy;
import com.vibe_guide.domain.trait.dtos.TraitCarouselResponseDTO;
import com.vibe_guide.domain.trait.dtos.TraitResponseDTO;
import com.vibe_guide.domain.trait.entities.Trait;
import com.vibe_guide.domain.trait.mappers.TraitMapper;
import com.vibe_guide.domain.trait.repositories.TraitRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TraitQueryServiceImpl implements TraitQueryService {

  private final TraitRepository traitRepository;
  private final TraitMapper traitMapper;

  @Override
  public Page<TraitResponseDTO> getPaginated(
      TraitType traitType, TraitSortBy sortBy, SortDirection sortDirection, int page, int size) {
    TraitSortBy actualSortBy = (sortBy != null) ? sortBy : TraitSortBy.DEFAULT;

    String sortField =
        switch (actualSortBy) {
          case DEFAULT -> "id";
          case NAME -> "name";
        };
    Sort sort =
        Sort.by(
            sortDirection == SortDirection.DESC
                ? Sort.Order.desc(sortField)
                : Sort.Order.asc(sortField));

    PageRequest pageRequest = PageRequest.of(page, size, sort);
    Page<Trait> traitPage;
    if (traitType == null) traitPage = traitRepository.findAll(pageRequest);
    else traitPage = traitRepository.findAllByTraitType(traitType, pageRequest);

    return traitPage.map(traitMapper::toTraitResponseDTO);
  }

  @Override
  public List<TraitCarouselResponseDTO> getAll() {
    List<Trait> traits = traitRepository.findAll();

    return traits.stream().map(traitMapper::toTraitCarouselResponseDTO).toList();
  }
}


