package com.vibe_guide.placeadmin.services.impl;

import com.vibe_guide.placeadmin.services.PlaceAdminManagementService;
import com.vibe_guide.exceptions.AdminForPlaceAlreadyExistsException;
import com.vibe_guide.exceptions.AdminForPlaceNotFound;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.exceptions.UserNotFoundException;
import com.vibe_guide.place.entities.Place;
import com.vibe_guide.place.repositories.PlaceRepository;
import com.vibe_guide.placeadmin.dtos.PlaceAdminRequestDTO;
import com.vibe_guide.placeadmin.entities.PlaceAdmin;
import com.vibe_guide.placeadmin.entities.PlaceAdminId;
import com.vibe_guide.placeadmin.repositories.PlaceAdminRepository;
import com.vibe_guide.placeadmin.utils.PlaceAdminResponseMessages;
import com.vibe_guide.user.entities.User;
import com.vibe_guide.user.repositories.UserRepository;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PlaceAdminManagementServiceImpl implements PlaceAdminManagementService {

  private final PlaceAdminRepository placeAdminRepository;
  private final PlaceRepository placeRepository;
  private final UserRepository userRepository;

  @Transactional
  @Override
  public String insert(UUID placeId, PlaceAdminRequestDTO placeAdminRequestDTO) {
    Place place =
        placeRepository.findById(placeId).orElseThrow(() -> new PlaceNotFoundException(placeId));

    UUID userId = placeAdminRequestDTO.userId();
    User user =
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

    PlaceAdminId placeAdminId = new PlaceAdminId(placeId, userId);
    if (placeAdminRepository.existsById(placeAdminId)) {
      throw new AdminForPlaceAlreadyExistsException(placeId, userId);
    }

    PlaceAdmin placeAdmin = new PlaceAdmin();
    placeAdmin.setPlace(place);
    placeAdmin.setUser(user);
    placeAdmin.setId(placeAdminId);

    placeAdminRepository.save(placeAdmin);

    return String.format(PlaceAdminResponseMessages.PLACE_ADMIN_INSERT_MESSAGE, userId, placeId);
  }

  @Override
  public String delete(UUID placeId, UUID userId) {
    PlaceAdminId placeAdminId = new PlaceAdminId(placeId, userId);
    if (!placeAdminRepository.existsById(placeAdminId)) {
      throw new AdminForPlaceNotFound(placeId, userId);
    }

    placeAdminRepository.deleteById(placeAdminId);

    return String.format(PlaceAdminResponseMessages.PLACE_ADMIN_DELETE_MESSAGE, userId, placeId);
  }
}

