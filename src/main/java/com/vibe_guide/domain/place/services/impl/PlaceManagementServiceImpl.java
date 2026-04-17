package com.vibe_guide.domain.place.services.impl;

import com.vibe_guide.domain.placetrait.dtos.BatchInsertTraitsInPlace;
import com.vibe_guide.domain.placetrait.dtos.PlaceTraitRequestDTO;
import com.vibe_guide.domain.placetrait.mappers.PlaceTraitMapper;
import com.vibe_guide.domain.placetrait.services.PlaceTraitManagementService;
import com.vibe_guide.domain.place.dtos.PlaceCreateDTO;
import com.vibe_guide.domain.place.dtos.PlaceCreateTraitDTO;
import com.vibe_guide.domain.place.services.PlaceManagementService;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.exceptions.TraitNotFoundException;
import com.vibe_guide.exceptions.TraitsAlreadyPresentForPlaceException;
import com.vibe_guide.domain.place.dtos.PlaceUpdateDTO;
import com.vibe_guide.domain.place.dtos.PlaceResponseDTO;
import com.vibe_guide.domain.place.entities.Place;
import com.vibe_guide.domain.place.mappers.PlaceMapper;
import com.vibe_guide.domain.place.repositories.PlaceRepository;
import com.vibe_guide.domain.place.utils.PlaceResponseMessages;
import com.vibe_guide.domain.placegallery.services.PlaceGalleryManagementService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Service
public class PlaceManagementServiceImpl implements PlaceManagementService {
  private PlaceRepository placeRepository;
  private PlaceGalleryManagementService placeGalleryManagementService;
  private PlaceTraitManagementService placeTraitManagementService;
  private PlaceTraitMapper placeTraitMapper;
  private PlaceMapper placeMapper;

  /**
   * Creates a new place and optionally uploads its initial gallery images and traits.
   *
   * <p>This method is intended for the initial place creation flow. Place information is persisted
   * first so the generated place ID can be used for both gallery storage uploads and initial trait
   * assignments.
   *
   * @param placeCreateDTO payload containing the initial place information, optional images, and
   *     optional traits
   * @return the created place mapped to a response DTO, including any persisted gallery image URLs
   * @throws TraitNotFoundException if any submitted trait ID does not exist
   * @throws TraitsAlreadyPresentForPlaceException if duplicate traits are submitted for the same
   *     place
   */
  @Override
  @Transactional
  public PlaceResponseDTO create(PlaceCreateDTO placeCreateDTO) {
    Place place = placeMapper.toPlace(placeCreateDTO);
    List<MultipartFile> images = placeCreateDTO.images();
    List<PlaceCreateTraitDTO> traits = placeCreateDTO.traits();

    Place savedPlace = placeRepository.save(place);

    if (traits != null && !traits.isEmpty()) {
      createInitialTraits(savedPlace.getId(), traits);
    }

    if (images != null && !images.isEmpty()) {
      placeGalleryManagementService.insertAll(savedPlace, images);
    }

    return placeMapper.toPlaceResponseDTO(savedPlace);
  }

  /**
   * Updates only the core informational fields of an existing place.
   *
   * <p>Gallery changes are intentionally excluded from this flow and must be handled through the
   * dedicated place gallery endpoints to avoid mixing metadata updates with storage-heavy image
   * operations.
   *
   * @param placeUpdateDTO payload containing the place ID and updated informational fields
   * @return success message describing the updated place
   * @throws PlaceNotFoundException if the requested place does not exist
   */
  @Transactional
  @Override
  public String update(PlaceUpdateDTO placeUpdateDTO) {
    UUID placeId = placeUpdateDTO.placeId();
    Place place = getById(placeId);
    String name = placeUpdateDTO.name();

    placeMapper.applyRequest(placeUpdateDTO, place);

    return String.format(PlaceResponseMessages.PLACE_UPDATE_MESSAGE, name);
  }

  /**
   * Deletes a place and removes all gallery assets associated with it.
   *
   * @param placeId ID of the place to delete
   * @return success message describing the deleted place
   * @throws PlaceNotFoundException if the requested place does not exist
   */
  @Transactional
  @Override
  public String delete(UUID placeId) {
    Place place = getById(placeId);

    placeGalleryManagementService.deleteAll(place);
    placeRepository.deleteById(placeId);

    String placeName = place.getName();
    return String.format(PlaceResponseMessages.PLACE_DELETE_MESSAGE, placeId, placeName);
  }

  /**
   * Retrieves a place by ID.
   *
   * @param placeId ID of the place to retrieve
   * @return the resolved place entity
   * @throws PlaceNotFoundException if the requested place does not exist
   */
  private Place getById(UUID placeId) {
    return placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));
  }

  /**
   * Converts initial create-trait payloads into place-trait requests and delegates the batch
   * creation to the place-trait management service.
   *
   * @param placeId ID of the newly created place
   * @param traits create-time trait payloads that should be assigned to the place
   * @throws TraitNotFoundException if any submitted trait ID does not exist
   * @throws TraitsAlreadyPresentForPlaceException if duplicate traits are submitted for the same
   *     place
   */
  private void createInitialTraits(UUID placeId, List<PlaceCreateTraitDTO> traits) {
    List<PlaceTraitRequestDTO> traitRequests =
        traits.stream()
            .map(trait -> placeTraitMapper.toPlaceTraitRequestDTO(placeId, trait))
            .toList();

    placeTraitManagementService.insertAll(new BatchInsertTraitsInPlace(traitRequests));
  }
}

