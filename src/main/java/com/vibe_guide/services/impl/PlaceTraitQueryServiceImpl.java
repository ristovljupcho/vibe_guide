package com.vibe_guide.services.impl;

import com.vibe_guide.converters.TraitConverter;
import com.vibe_guide.dtos.TopTraitResponseDTO;
import com.vibe_guide.dtos.TraitCarouselResponseDTO;
import com.vibe_guide.entities.Place;
import com.vibe_guide.entities.Trait;
import com.vibe_guide.entities.views.TraitLikesSummary;
import com.vibe_guide.repositories.PlaceTraitRepository;
import com.vibe_guide.services.PlaceTraitQueryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PlaceTraitQueryServiceImpl implements PlaceTraitQueryService {

    private final PlaceTraitRepository placeTraitRepository;
    private final TraitConverter traitConverter;

    /**
     * Retrieves a list of traits for a given place and converts them into DTOs for the carousel display.
     *
     * @param placeId {@link Place} ID for which we retrieve {@link Trait} objects.
     * @return List of {@link TraitCarouselResponseDTO} containing trait names.
     */
    @Override
    public List<TraitCarouselResponseDTO> getTraitsForPlaceCarousel(UUID placeId) {
        List<Trait> traits = placeTraitRepository.getTraitsForPlaceCarousel(placeId);

        return traits.stream().map(traitConverter::toTraitMenuResponseDTO).toList();
    }

    /**
     * Retrieves the top 10 most popular traits based on their like count.
     * <p>
     * This method fetches trait popularity data from the repository,
     * limits the results to the top 10, and converts them into DTOs
     * for use in the response.
     * </p>
     *
     * @return List of {@link TopTraitResponseDTO} containing the most popular traits.
     */
    @Override
    public List<TopTraitResponseDTO> getMostPopularTraits() {
        List<TraitLikesSummary> traitLikesSummaries = placeTraitRepository.getTopTraits().stream().limit(10).toList();

        return traitLikesSummaries.stream().map(traitConverter::toTopTraitResponseDTO).toList();
    }
}