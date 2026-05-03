package com.vibe_guide.domain.user.services.impl;

import com.vibe_guide.domain.user.dtos.UserPreviewResponseDTO;
import com.vibe_guide.domain.user.dtos.UserUpsertRequestDTO;
import com.vibe_guide.domain.user.entities.User;
import com.vibe_guide.domain.user.mappers.UserMapper;
import com.vibe_guide.domain.user.repositories.UserRepository;
import com.vibe_guide.domain.user.services.UserUpsertService;
import com.vibe_guide.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserUpsertServiceImpl implements UserUpsertService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Override
  @Transactional
  public UserPreviewResponseDTO upsert(UserUpsertRequestDTO dto) {
    User user = userRepository.findById(dto.id()).orElseGet(User::new);

    user.setId(dto.id());
    user.setUsername(dto.username());
    user.setName(dto.name());
    user.setEmail(dto.email());

    if (user.getRole() == null) {
      user.setRole(Role.USER);
    }

    userRepository.save(user);
    return userMapper.toUserPreviewResponseDTO(user);
  }
}
