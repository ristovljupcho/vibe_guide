package com.vibe_guide.services.impl;

import com.vibe_guide.converters.TraitConverter;
import com.vibe_guide.dtos.TraitResponseDTO;
import com.vibe_guide.entities.Trait;
import com.vibe_guide.enums.sorting.SortDirection;
import com.vibe_guide.enums.sorting.TraitSortBy;
import com.vibe_guide.enums.TraitType;
import com.vibe_guide.repositories.TraitRepository;
import com.vibe_guide.services.TraitQueryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TraitQueryServiceImpl implements TraitQueryService {

    private final TraitRepository traitRepository;
    private final TraitConverter traitConverter;

    /**
     * Retrieves {@link Trait} objects using pagination. Filtering is enabled using {@link TraitType} which will
     * display {@link Trait} objects with a certain type. Sorting by name or default (UUID), in ASC or DESC order.
     *
     * @param traitType     can be <u><i>null</i></u>, used for filtering {@link Trait} objects.
     * @param sortBy        used for sorting, default trait sort criteria is <b><i>TYPE</i></b> from enum
     *                      {@link TraitSortBy}.
     * @param sortDirection used for sorting direction, default sort direction is <b><i>ASC</i></b> from enum
     *                      {@link SortDirection}.
     * @param page          page number.
     * @param size          size of the page to be returned.
     * @return A {@link Page} containing {@link TraitResponseDTO} objects.
     */
    @Override
    public Page<TraitResponseDTO> getPaginatedTraits(TraitType traitType, TraitSortBy sortBy,
                                                     SortDirection sortDirection, int page, int size) {
        String sortField = switch (sortBy) {
            case DEFAULT -> "id";
            case NAME -> "name";
        };
        Sort sort = Sort.by(sortDirection == SortDirection.DESC ? Sort.Order.desc(sortField) :
                Sort.Order.asc(sortField));

        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<Trait> traitPage;
        if (traitType == null)
            traitPage = traitRepository.findAll(pageRequest);
        else traitPage = traitRepository.findAllByTraitType(traitType, pageRequest);

        return traitPage.map(traitConverter::toTraitResponseDTO);
    }
}