package com.vibe_guide.domain.placeadmin.services.impl;

import com.vibe_guide.domain.placeadmin.services.PlaceAdminQueryService;
import com.vibe_guide.exceptions.PlaceNotFoundException;
import com.vibe_guide.domain.place.repositories.PlaceRepository;
import com.vibe_guide.domain.placeadmin.dtos.PlaceAdminResponseDTO;
import com.vibe_guide.domain.placeadmin.repositories.PlaceAdminRepository;
import com.vibe_guide.domain.user.entities.User;
import com.vibe_guide.domain.user.mappers.UserMapper;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PlaceAdminQueryServiceImpl implements PlaceAdminQueryService {

  private final PlaceAdminRepository placeAdminRepository;
  private final PlaceRepository placeRepository;
  private final UserMapper userMapper;

  @Override
  public List<PlaceAdminResponseDTO> getAllByPlaceId(UUID placeId) {
    if (!placeRepository.existsById(placeId)) {
      throw new PlaceNotFoundException(placeId);
    }

    List<User> users = placeAdminRepository.findAllByPlaceId(placeId);

    return users.stream().map(userMapper::toPlaceAdminResponseDTO).toList();
  }
}


