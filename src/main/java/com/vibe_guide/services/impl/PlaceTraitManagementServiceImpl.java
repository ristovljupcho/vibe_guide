package com.vibe_guide.services.impl;

import com.vibe_guide.dtos.BatchDeleteTraitsInPlace;
import com.vibe_guide.dtos.BatchInsertTraitsInPlace;
import com.vibe_guide.dtos.PlaceTraitRequestDTO;
import com.vibe_guide.entities.Place;
import com.vibe_guide.entities.PlaceTrait;
import com.vibe_guide.entities.Trait;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.exceptions.TraitAlreadyPresentForPlaceException;
import com.vibe_guide.exceptions.TraitForPlaceNotFound;
import com.vibe_guide.exceptions.TraitNotFoundException;
import com.vibe_guide.exceptions.TraitsAlreadyPresentForPlaceException;
import com.vibe_guide.exceptions.TraitsNotFoundException;
import com.vibe_guide.repositories.PlaceRepository;
import com.vibe_guide.repositories.PlaceTraitRepository;
import com.vibe_guide.repositories.TraitRepository;
import com.vibe_guide.services.PlaceTraitManagementService;
import com.vibe_guide.utils.PlaceTraitResponseMessages;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PlaceTraitManagementServiceImpl implements PlaceTraitManagementService {
    private final PlaceRepository placeRepository;
    private final TraitRepository traitRepository;
    private final PlaceTraitRepository placeTraitRepository;

    /**
     * Inserts a single {@link Trait} for {@link Place} with provided ID into the db.
     *
     * @param placeId                    ID of {@link Place}.
     * @param placeTraitInsertRequestDTO Request DTO containing needed data.
     * @return Response message of type {@link PlaceTraitResponseMessages}.
     */
    @Transactional
    @Override
    public String insertSingleTraitInPlace(UUID placeId, PlaceTraitRequestDTO placeTraitInsertRequestDTO) {
        Place place = getPlace(placeId);
        UUID traitId = placeTraitInsertRequestDTO.traitId();
        Trait trait = getTrait(traitId);
        if (placeTraitRepository.findByPlaceIdAndTraitId(placeId, traitId).isPresent()) {
            throw new TraitAlreadyPresentForPlaceException(placeId, traitId);
        }
        String additionalInformation = placeTraitInsertRequestDTO.additionalInformation();

        PlaceTrait placeTrait = new PlaceTrait();
        placeTrait.setPlace(place);
        placeTrait.setTrait(trait);
        placeTrait.setAdditionalInformation(additionalInformation);

        placeTraitRepository.save(placeTrait);

        return String.format(PlaceTraitResponseMessages.PLACE_TRAIT_INSERT_MESSAGE, placeId);
    }

    /**
     * Multiple insertion of {@link Trait}s for {@link Place} with provided ID into the db.
     *
     * @param placeId                  ID of {@link Place}.
     * @param batchInsertTraitsInPlace Request DTO containing needed data.
     * @return Response message of type {@link PlaceTraitResponseMessages}.
     */
    @Transactional
    @Override
    public String batchInsertTraitsInPlace(UUID placeId, BatchInsertTraitsInPlace batchInsertTraitsInPlace) {
        Place place = getPlace(placeId);
        List<PlaceTraitRequestDTO> placeTraitRequestDTOs = batchInsertTraitsInPlace.placeTraitRequestDTOs();
        List<UUID> requestTraitIds = placeTraitRequestDTOs.stream()
                .map(PlaceTraitRequestDTO::traitId)
                .toList();

        List<Trait> allTraits = traitRepository.findAllById(requestTraitIds);
        Map<UUID, Trait> allTraitsMap = allTraits.stream()
                .collect(Collectors.toMap(Trait::getId, t -> t));

        List<UUID> missingTraits = requestTraitIds.stream()
                .filter(id -> !allTraitsMap.containsKey(id))
                .toList();
        if (!missingTraits.isEmpty()) {
            throw new TraitsNotFoundException(convertTraitIdsToString(missingTraits));
        }

        List<UUID> allTraitsByPlace = placeTraitRepository
                .findAllTraitIdsByPlaceId(placeId);
        List<UUID> conflictingTraitIds = requestTraitIds.stream()
                .filter(allTraitsByPlace::contains)
                .toList();
        if (!conflictingTraitIds.isEmpty()) {
            throw new TraitsAlreadyPresentForPlaceException(
                    placeId, convertTraitIdsToString(conflictingTraitIds)
            );
        }

        List<PlaceTrait> newPlaceTraits = new ArrayList<>();
        for (PlaceTraitRequestDTO dto : placeTraitRequestDTOs) {
            UUID traitId = dto.traitId();
            Trait trait = allTraitsMap.get(traitId);
            PlaceTrait placeTrait = new PlaceTrait();
            placeTrait.setPlace(place);
            placeTrait.setTrait(trait);
            placeTrait.setAdditionalInformation(dto.additionalInformation());
            newPlaceTraits.add(placeTrait);
        }

        placeTraitRepository.saveAll(newPlaceTraits);

        return String.format(PlaceTraitResponseMessages.PLACE_TRAIT_BATCH_INSERT_MESSAGE,
                newPlaceTraits.size(), placeId);
    }

    /**
     * Update a single {@link Trait} for {@link Place} with provided ID into the db.
     *
     * @param placeId                    ID of {@link Place}.
     * @param placeTraitUpdateRequestDTO Request DTO containing needed data.
     * @return Response message of type {@link PlaceTraitResponseMessages}.
     */
    @Transactional
    @Override
    public String updateTraitForPlace(UUID placeId, PlaceTraitRequestDTO placeTraitUpdateRequestDTO) {
        UUID traitId = placeTraitUpdateRequestDTO.traitId();
        PlaceTrait placeTrait = getPlaceTrait(placeId, traitId);
        String additionalInformation = placeTraitUpdateRequestDTO.additionalInformation();

        placeTrait.setAdditionalInformation(additionalInformation);

        placeTraitRepository.save(placeTrait);

        return String.format(PlaceTraitResponseMessages.PLACE_TRAIT_UPDATE_MESSAGE, placeId);
    }

    /**
     * Delete a single {@link Trait} for {@link Place} with provided ID of both {@link Trait} and {@link Place}.
     *
     * @param placeId ID of {@link Place}.
     * @param traitId ID of {@link Trait}.
     * @return Response message of type {@link PlaceTraitResponseMessages}.
     */
    @Transactional
    @Override
    public String deleteSingleTraitInPlace(UUID placeId, UUID traitId) {
        PlaceTrait placeTrait = getPlaceTrait(placeId, traitId);

        placeTraitRepository.delete(placeTrait);

        return String.format(PlaceTraitResponseMessages.PLACE_TRAIT_DELETE_MESSAGE, placeId);
    }

    /**
     * Multiple deletion of {@link Trait}s for {@link Place} with provided ID of both {@link Trait}s and {@link Place}.
     *
     * @param placeId                  ID of {@link Place}.
     * @param batchDeleteTraitsInPlace Request DTO containing needed data.
     * @return Response message of type {@link PlaceTraitResponseMessages}.
     */
    @Transactional
    @Override
    public String batchDeleteTraitsInPlace(UUID placeId, BatchDeleteTraitsInPlace batchDeleteTraitsInPlace) {
        if (!placeRepository.existsById(placeId)) {
            throw new PlaceNotFoundException(placeId);
        }

        List<UUID> traitsToDelete = batchDeleteTraitsInPlace.traitIds();
        Map<UUID, PlaceTrait> placeTraitMap = placeTraitRepository.findAllByPlaceId(placeId).stream()
                .collect(Collectors.toMap(pt -> pt.getTrait().getId(), pt -> pt));

        Set<UUID> existingTraitIds = placeTraitMap.keySet();
        List<UUID> invalidTraits = traitsToDelete.stream()
                .filter(traitId -> !existingTraitIds.contains(traitId))
                .toList();
        if (!invalidTraits.isEmpty()) {
            throw new TraitsNotFoundException(convertTraitIdsToString(invalidTraits));
        }

        List<PlaceTrait> toDelete = traitsToDelete.stream()
                .map(placeTraitMap::get)
                .toList();

        placeTraitRepository.deleteAll(toDelete);

        return String.format(PlaceTraitResponseMessages.PLACE_TRAIT_BATCH_DELETE_MESSAGE, toDelete.size(), placeId);
    }


    /**
     * Converts a list of {@link UUID} of {@link Trait}s into a single {@link String}.
     *
     * @param traitIds List of IDs of {@link Trait}s.
     * @return String of IDs joined by ', '.
     */
    private String convertTraitIdsToString(List<UUID> traitIds) {
        return traitIds.stream().map(UUID::toString).collect(Collectors.joining(", "));
    }

    private Place getPlace(UUID placeId) {
        return placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));
    }

    private Trait getTrait(UUID traitId) {
        return traitRepository.findById(traitId).orElseThrow(() -> new TraitNotFoundException(traitId));
    }

    private PlaceTrait getPlaceTrait(UUID placeId, UUID traitId) {
        return placeTraitRepository.findByPlaceIdAndTraitId(placeId, traitId)
                .orElseThrow(() -> new TraitForPlaceNotFound(placeId, traitId));
    }
}