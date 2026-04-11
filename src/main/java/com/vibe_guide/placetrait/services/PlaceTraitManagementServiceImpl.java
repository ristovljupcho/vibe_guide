package com.vibe_guide.placetrait.services;

import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.exceptions.TraitAlreadyPresentForPlaceException;
import com.vibe_guide.exceptions.TraitForPlaceNotFound;
import com.vibe_guide.exceptions.TraitNotFoundException;
import com.vibe_guide.exceptions.TraitsAlreadyPresentForPlaceException;
import com.vibe_guide.exceptions.TraitsNotFoundException;
import com.vibe_guide.place.entities.Place;
import com.vibe_guide.place.repositories.PlaceRepository;
import com.vibe_guide.placetrait.dtos.BatchDeleteTraitsInPlace;
import com.vibe_guide.placetrait.dtos.BatchInsertTraitsInPlace;
import com.vibe_guide.placetrait.dtos.PlaceTraitRequestDTO;
import com.vibe_guide.placetrait.entities.PlaceTrait;
import com.vibe_guide.placetrait.repositories.PlaceTraitRepository;
import com.vibe_guide.placetrait.utils.PlaceTraitResponseMessages;
import com.vibe_guide.trait.entities.Trait;
import com.vibe_guide.trait.repositories.TraitRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service implementation responsible for managing {@link PlaceTrait} associations. Handles
 * insertion, batch insertion, updating, and deletion of traits for a specific {@link Place}.
 *
 * <p>All methods modifying data are transactional to ensure atomicity.
 */
@Service
@AllArgsConstructor
public class PlaceTraitManagementServiceImpl implements PlaceTraitManagementService {
  private final PlaceRepository placeRepository;
  private final TraitRepository traitRepository;
  private final PlaceTraitRepository placeTraitRepository;

  /**
   * Inserts a single {@link Trait} for a specific {@link Place}.
   *
   * @param dto the payload containing place ID, trait ID, additional information, and priority flag
   * @return success message defined in {@link PlaceTraitResponseMessages}
   * @throws PlaceNotFoundException if the place does not exist
   * @throws TraitNotFoundException if the trait does not exist
   * @throws TraitAlreadyPresentForPlaceException if the trait is already assigned to this place
   */
  @Transactional
  @Override
  public String insert(PlaceTraitRequestDTO dto) {

    UUID placeId = dto.placeId();
    Place place = getById(placeId);
    UUID traitId = dto.traitId();
    Trait trait = getTrait(traitId);

    if (placeTraitRepository.findByPlaceIdAndTraitId(placeId, traitId).isPresent()) {
      throw new TraitAlreadyPresentForPlaceException(placeId, traitId);
    }

    PlaceTrait placeTrait = new PlaceTrait();
    placeTrait.setPlace(place);
    placeTrait.setTrait(trait);
    placeTrait.setAdditionalInformation(dto.additionalInformation());
    placeTrait.setPriority(dto.priority());

    placeTraitRepository.save(placeTrait);

    return String.format(PlaceTraitResponseMessages.PLACE_TRAIT_INSERT_MESSAGE, placeId);
  }

  /**
   * Inserts multiple traits for a specific {@link Place}. Performs validation to ensure:
   *
   * <ul>
   *   <li>All trait IDs exist
   *   <li>No trait is already assigned to the place
   * </ul>
   *
   * @param request wrapper containing list of {@link PlaceTraitRequestDTO}
   * @return success message
   * @throws PlaceNotFoundException if the place does not exist
   * @throws TraitsNotFoundException if one or more trait IDs do not exist
   * @throws TraitsAlreadyPresentForPlaceException if one or more traits already exist for this
   *     place
   * @throws IllegalArgumentException if request list is empty
   */
  @Transactional
  @Override
  public String insertAll(BatchInsertTraitsInPlace request) {

    List<PlaceTraitRequestDTO> dtos = request.placeTraitRequestDTOs();
    if (dtos.isEmpty()) {
      throw new IllegalArgumentException("Trait list cannot be empty.");
    }

    UUID placeId = dtos.getFirst().placeId();
    Place place = getById(placeId);

    List<UUID> traitIds = dtos.stream().map(PlaceTraitRequestDTO::traitId).toList();

    validateTraitExistence(traitIds);

    // Check duplicates already assigned
    List<UUID> existingTraitIds = placeTraitRepository.findAllTraitIdsByPlaceId(placeId);
    List<UUID> duplicates = traitIds.stream().filter(existingTraitIds::contains).toList();

    if (!duplicates.isEmpty()) {
      throw new TraitsAlreadyPresentForPlaceException(placeId, convertTraitIdsToString(duplicates));
    }

    Map<UUID, Trait> traits =
        traitRepository.findAllById(traitIds).stream()
            .collect(Collectors.toMap(Trait::getId, t -> t));

    List<PlaceTrait> entities =
        dtos.stream()
            .map(
                dto -> {
                  PlaceTrait pt = new PlaceTrait();
                  pt.setPlace(place);
                  pt.setTrait(traits.get(dto.traitId()));
                  pt.setAdditionalInformation(dto.additionalInformation());
                  pt.setPriority(dto.priority());
                  return pt;
                })
            .toList();

    placeTraitRepository.saveAll(entities);

    return String.format(PlaceTraitResponseMessages.PLACE_TRAIT_BATCH_INSERT_MESSAGE, placeId);
  }

  /**
   * Updates a single {@link PlaceTrait} entry for a given place and trait.
   *
   * @param dto update content including additional info and priority
   * @return success message
   * @throws TraitForPlaceNotFound if the trait is not assigned to the place
   */
  @Transactional
  @Override
  public String update(PlaceTraitRequestDTO dto) {

    UUID placeId = dto.placeId();
    UUID traitId = dto.traitId();

    PlaceTrait placeTrait = getPlaceTrait(placeId, traitId);

    placeTrait.setAdditionalInformation(dto.additionalInformation());
    placeTrait.setPriority(dto.priority());

    placeTraitRepository.save(placeTrait);

    return String.format(PlaceTraitResponseMessages.PLACE_TRAIT_UPDATE_MESSAGE, placeId);
  }

  /**
   * Deletes a single trait assigned to a place.
   *
   * @param id ID of the placeTrait entry.
   * @return success message
   * @throws TraitForPlaceNotFound if the trait is not assigned to the place
   */
  @Transactional
  @Override
  public String delete(UUID id) {
    PlaceTrait placeTrait =
        placeTraitRepository.findById(id).orElseThrow(() -> new TraitForPlaceNotFound(id));
    placeTraitRepository.delete(placeTrait);

    return String.format(PlaceTraitResponseMessages.PLACE_TRAIT_DELETE_MESSAGE, id);
  }

  /**
   * Deletes multiple traits from a specific place. Ensures that all trait IDs exist for that place
   * before deletion.
   *
   * @param request wrapper containing list of trait IDs
   * @return success message
   * @throws PlaceNotFoundException if the place does not exist
   * @throws TraitsNotFoundException if one or more trait IDs are not assigned to the place
   */
  @Transactional
  @Override
  public String deleteAll(BatchDeleteTraitsInPlace request) {

    UUID placeId = request.placeId();
    if (!placeRepository.existsById(placeId)) {
      throw new PlaceNotFoundException(placeId);
    }

    List<UUID> traitIdsToDelete = request.traitIds();

    Map<UUID, PlaceTrait> placeTraitsMap =
        placeTraitRepository.findAllByPlaceId(placeId).stream()
            .collect(Collectors.toMap(pt -> pt.getTrait().getId(), pt -> pt));

    List<UUID> invalidIds =
        traitIdsToDelete.stream().filter(id -> !placeTraitsMap.containsKey(id)).toList();

    if (!invalidIds.isEmpty()) {
      throw new TraitsNotFoundException(convertTraitIdsToString(invalidIds));
    }

    List<PlaceTrait> entitiesToDelete = traitIdsToDelete.stream().map(placeTraitsMap::get).toList();

    placeTraitRepository.deleteAll(entitiesToDelete);

    return String.format(
        PlaceTraitResponseMessages.PLACE_TRAIT_BATCH_DELETE_MESSAGE,
        entitiesToDelete.size(),
        placeId);
  }

  /** Retrieves a {@link Place} by ID or throws exception. */
  private Place getById(UUID placeId) {
    return placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));
  }

  /** Retrieves a {@link Trait} by ID or throws exception. */
  private Trait getTrait(UUID traitId) {
    return traitRepository.findById(traitId).orElseThrow(() -> new TraitNotFoundException(traitId));
  }

  /** Retrieves a {@link PlaceTrait} based on place ID and trait ID. */
  private PlaceTrait getPlaceTrait(UUID placeId, UUID traitId) {
    return placeTraitRepository
        .findByPlaceIdAndTraitId(placeId, traitId)
        .orElseThrow(() -> new TraitForPlaceNotFound(placeId, traitId));
  }

  /**
   * Validates that all provided trait IDs exist.
   *
   * @throws TraitsNotFoundException if any trait ID does not exist
   */
  private void validateTraitExistence(List<UUID> traitIds) {
    List<UUID> existing = traitRepository.findAllById(traitIds).stream().map(Trait::getId).toList();

    List<UUID> missing = traitIds.stream().filter(id -> !existing.contains(id)).toList();

    if (!missing.isEmpty()) {
      throw new TraitsNotFoundException(convertTraitIdsToString(missing));
    }
  }

  /** Converts a list of UUIDs into a comma-separated string. */
  private String convertTraitIdsToString(List<UUID> traitIds) {
    return String.join(", ", traitIds.stream().map(UUID::toString).toList());
  }
}
