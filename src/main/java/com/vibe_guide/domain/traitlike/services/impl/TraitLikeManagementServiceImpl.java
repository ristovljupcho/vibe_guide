package com.vibe_guide.domain.traitlike.services.impl;

import com.vibe_guide.domain.traitlike.services.TraitLikeManagementService;
import static com.vibe_guide.domain.traitlike.utils.TraitLikeResponseMessages.TRAIT_LIKE_SUCCESSFUL_DELETE;
import static com.vibe_guide.domain.traitlike.utils.TraitLikeResponseMessages.TRAIT_LIKE_SUCCESSFUL_INSERT;

import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.exceptions.TraitForPlaceNotFound;
import com.vibe_guide.exceptions.UserNotFoundException;
import com.vibe_guide.domain.place.repositories.PlaceRepository;
import com.vibe_guide.domain.placetrait.entities.PlaceTrait;
import com.vibe_guide.domain.placetrait.repositories.PlaceTraitRepository;
import com.vibe_guide.domain.traitlike.dtos.TraitLikeRequestDTO;
import com.vibe_guide.domain.traitlike.entities.TraitLike;
import com.vibe_guide.domain.traitlike.repositories.TraitLikeRepository;
import com.vibe_guide.domain.traitlike.utils.TraitLikeResponseMessages;
import com.vibe_guide.domain.user.entities.User;
import com.vibe_guide.domain.user.repositories.UserRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TraitLikeManagementServiceImpl implements TraitLikeManagementService {

  private final UserRepository userRepository;
  private final PlaceRepository placeRepository;
  private final PlaceTraitRepository placeTraitRepository;
  private final TraitLikeRepository traitLikeRepository;

  /**
   * Adds likes from a given user to the specified traits for a place.
   *
   * <p>This method:
   *
   * <ul>
   *   <li>Validates user and place existence
   *   <li>Ensures each trait belongs to that place
   *   <li>Prevents duplicating likes
   *   <li>Increments the like counter for newly liked traits
   *   <li>Persists new likes and counter updates
   * </ul>
   *
   * @param dto request containing userId, placeId, and traitIds
   * @return confirmation message of type {@link TraitLikeResponseMessages}
   * @throws UserNotFoundException if user does not exist
   * @throws PlaceNotFoundException if place does not exist
   * @throws TraitForPlaceNotFound if any trait does not belong to the place
   */
  @Transactional
  @Override
  public String insert(TraitLikeRequestDTO dto) {
    UUID userId = dto.userId();
    User user = loadUser(userId);

    UUID placeId = dto.placeId();
    validatePlace(placeId);

    List<UUID> traitIds = dto.traitIds();
    Map<UUID, PlaceTrait> placeTraitMap = loadAndValidatePlaceTraits(placeId, traitIds);

    Map<UUID, TraitLike> existingLikes = loadExistingLikes(userId, placeTraitMap.values());

    List<TraitLike> toCreate = new ArrayList<>();
    List<PlaceTrait> updatedCounters = new ArrayList<>();

    for (UUID traitId : traitIds) {
      PlaceTrait pt = placeTraitMap.get(traitId);

      if (existingLikes.containsKey(pt.getId())) {
        continue;
      }

      TraitLike like = TraitLike.builder().placeTrait(pt).user(user).build();

      toCreate.add(like);

      pt.setLikeCounter(pt.getLikeCounter() + 1);
      updatedCounters.add(pt);
    }

    if (!toCreate.isEmpty()) {
      traitLikeRepository.saveAll(toCreate);
    }
    if (!updatedCounters.isEmpty()) {
      placeTraitRepository.saveAll(updatedCounters);
    }

    return TRAIT_LIKE_SUCCESSFUL_INSERT;
  }

  /**
   * Removes likes from a user for the specified traits associated with a place.
   *
   * <p>This method:
   *
   * <ul>
   *   <li>Validates user and place existence
   *   <li>Ensures traits belong to the place
   *   <li>Deletes only existing likes
   *   <li>Decrements like counters safely (never below zero)
   * </ul>
   *
   * @param dto request containing userId, placeId, and traitIds
   * @return confirmation message of type {@link TraitLikeResponseMessages}
   * @throws UserNotFoundException if user does not exist
   * @throws PlaceNotFoundException if place does not exist
   * @throws TraitForPlaceNotFound if trait is not associated with the place
   */
  @Transactional
  @Override
  public String delete(TraitLikeRequestDTO dto) {
    UUID userId = dto.userId();
    loadUser(userId);

    UUID placeId = dto.placeId();
    validatePlace(placeId);

    List<UUID> traitIds = dto.traitIds();
    Map<UUID, PlaceTrait> placeTraitMap = loadAndValidatePlaceTraits(placeId, traitIds);

    Map<UUID, TraitLike> existingLikes = loadExistingLikes(userId, placeTraitMap.values());

    List<TraitLike> toDelete = new ArrayList<>();
    List<PlaceTrait> updatedCounters = new ArrayList<>();

    for (UUID traitId : traitIds) {
      PlaceTrait pt = placeTraitMap.get(traitId);

      TraitLike like = existingLikes.get(pt.getId());
      if (like == null) continue;

      toDelete.add(like);
      pt.setLikeCounter(Math.max(pt.getLikeCounter() - 1, 0));
      updatedCounters.add(pt);
    }

    if (!toDelete.isEmpty()) {
      traitLikeRepository.deleteAll(toDelete);
    }
    if (!updatedCounters.isEmpty()) {
      placeTraitRepository.saveAll(updatedCounters);
    }

    return TRAIT_LIKE_SUCCESSFUL_DELETE;
  }

  /**
   * Loads a user by ID or throws an exception.
   *
   * @param userId ID of the user to load
   * @return the user entity
   * @throws UserNotFoundException if the user does not exist
   */
  private User loadUser(UUID userId) {
    return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
  }

  /**
   * Validates that the place exists.
   *
   * @param placeId ID of the place to validate
   * @throws PlaceNotFoundException if the place does not exist
   */
  private void validatePlace(UUID placeId) {
    placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));
  }

  /**
   * Loads the traits for a given place and validates that all provided traitIds belong to that
   * place.
   *
   * @param placeId the place ID
   * @param traitIds the list of trait IDs to validate
   * @return map of traitId ÃƒÂ¢Ã¢â‚¬Â Ã¢â‚¬â„¢ PlaceTrait entity
   * @throws TraitForPlaceNotFound if any trait is not associated with the specified place
   */
  private Map<UUID, PlaceTrait> loadAndValidatePlaceTraits(UUID placeId, List<UUID> traitIds) {

    List<PlaceTrait> placeTraits =
        placeTraitRepository.findAllByPlaceIdAndTraitIdIn(placeId, traitIds);

    Map<UUID, PlaceTrait> placeTraitMap =
        placeTraits.stream().collect(Collectors.toMap(pt -> pt.getTrait().getId(), pt -> pt));

    List<UUID> missing = traitIds.stream().filter(id -> !placeTraitMap.containsKey(id)).toList();

    if (!missing.isEmpty()) {
      throw new TraitForPlaceNotFound(placeId, missing.getFirst());
    }

    return placeTraitMap;
  }

  /**
   * Loads all existing likes for the given user related to the provided place traits.
   *
   * @param userId the user ID
   * @param placeTraits the place traits to check against
   * @return map of placeTraitId ÃƒÂ¢Ã¢â‚¬Â Ã¢â‚¬â„¢ TraitLike
   */
  private Map<UUID, TraitLike> loadExistingLikes(UUID userId, Collection<PlaceTrait> placeTraits) {

    List<UUID> placeTraitIds = placeTraits.stream().map(PlaceTrait::getId).toList();

    List<TraitLike> likes =
        traitLikeRepository.findAllByUserIdAndPlaceTraitIdIn(userId, placeTraitIds);

    return likes.stream().collect(Collectors.toMap(tl -> tl.getPlaceTrait().getId(), tl -> tl));
  }
}


