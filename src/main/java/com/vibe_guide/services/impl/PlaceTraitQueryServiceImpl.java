package com.vibe_guide.services.impl;

import com.vibe_guide.converters.TraitConverter;
import com.vibe_guide.dtos.TraitCarouselResponseDTO;
import com.vibe_guide.dtos.TraitResponseDTO;
import com.vibe_guide.entities.Place;
import com.vibe_guide.entities.Trait;
import com.vibe_guide.entities.views.TraitLikesSummary;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.repositories.PlaceRepository;
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
    private final PlaceRepository placeRepository;

    /**
     * Retrieves a list of {@link Trait} objects for a given place and converts them into DTOs for the carousel display.
     *
     * @param placeId {@link Place} for which we retrieve {@link Trait} objects.
     * @return List of {@link TraitCarouselResponseDTO} containing trait names.
     */
    @Override
    public List<TraitCarouselResponseDTO> getTraitsForDisplayInPlaceCarousel(UUID placeId) {
        checkIfPlaceExists(placeId);
        List<Trait> traits = placeTraitRepository.getTraitsForPlaceCarousel(placeId);

        return traits.stream().map(traitConverter::toTraitCarouselResponseDTO).toList();
    }

    /**
     * Retrieves top 5 {@link Trait} objects for a given {@link Place} object with provided ID.
     *
     * @param placeId {@link Place} for which we retrieve missing {@link Trait} objects.
     * @return List of {@link TraitResponseDTO} containing all {@link Trait} data.
     */
    @Override
    public List<TraitResponseDTO> getTopTraitsForPlace(UUID placeId) {
        checkIfPlaceExists(placeId);
        List<Trait> traits = placeTraitRepository.getTopTraitsForPlace(placeId);

        return traits.stream().map(traitConverter::toTraitResponseDTO).toList();
    }

    /**
     * Retrieves all missing {@link Trait} objects for a given {@link Place} object with provided ID.
     *
     * @param placeId {@link Place} for which we retrieve missing {@link Trait} objects.
     * @return List of {@link TraitResponseDTO} containing all {@link Trait} data.
     */
    @Override
    public List<TraitResponseDTO> getMissingTraitsForPlace(UUID placeId) {
        checkIfPlaceExists(placeId);
        List<Trait> missingTraits = placeTraitRepository.getMissingTraitsForPlace(placeId);

        return missingTraits.stream().map(traitConverter::toTraitResponseDTO).toList();
    }

    /**
     * Retrieves the top 10 most popular traits based on their like count.
     * <p>
     * This method fetches trait popularity data from the repository,
     * limits the results to the top 10, and converts them into DTOs
     * for use in the response.
     * </p>
     *
     * @return List of {@link TraitResponseDTO} containing the most popular traits.
     */
    @Override
    public List<TraitResponseDTO> getMostPopularTraits() {
        List<TraitLikesSummary> traitLikesSummaries = placeTraitRepository.getTopTraits().stream().limit(10).toList();

        return traitLikesSummaries.stream().map(traitConverter::toTraitResponseDTO).toList();
    }

    void checkIfPlaceExists(UUID placeId) {
        if (!placeRepository.existsById(placeId)) {
            throw new PlaceNotFoundException(placeId);
        }
    }
}