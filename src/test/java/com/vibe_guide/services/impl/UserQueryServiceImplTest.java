package com.vibe_guide.services.impl;

import com.vibe_guide.converters.UserConverter;
import com.vibe_guide.data.TestDataFactory;
import com.vibe_guide.dtos.UserPreviewResponseDTO;
import com.vibe_guide.entities.User;
import com.vibe_guide.enums.Role;
import com.vibe_guide.enums.sorting.SortDirection;
import com.vibe_guide.enums.sorting.UserSortBy;
import com.vibe_guide.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserQueryServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserConverter userConverter;

    @InjectMocks
    private UserQueryServiceImpl service;

    @Test
    void getPaginatedUsersMapsEntitiesToDtos() {
        User user = TestDataFactory.user(TestDataFactory.uuid());
        UserPreviewResponseDTO dto = TestDataFactory.userPreviewResponseDto(user.getId());
        Page<User> page = new PageImpl<>(List.of(user), PageRequest.of(0, 10), 1);
        when(userRepository.findAll(any())).thenReturn(page);
        when(userConverter.toUserPreviewResponseDTO(user)).thenReturn(dto);

        Page<UserPreviewResponseDTO> result = service.getPaginatedUsers(Role.USER, UserSortBy.USERNAME,
                SortDirection.ASC, 0, 10);

        assertEquals(1, result.getTotalElements());
        assertEquals(dto, result.getContent().getFirst());
    }

    @Test
    void getUserByIdReturnsMappedDto() {
        User user = TestDataFactory.user(TestDataFactory.uuid());
        UserPreviewResponseDTO dto = TestDataFactory.userPreviewResponseDto(user.getId());
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userConverter.toUserPreviewResponseDTO(user)).thenReturn(dto);

        UserPreviewResponseDTO result = service.getUserById(user.getId());

        assertEquals(dto, result);
    }

    @Test
    void getUserByUsernameDelegatesToRepository() {
        User user = TestDataFactory.user(TestDataFactory.uuid());
        UserPreviewResponseDTO dto = TestDataFactory.userPreviewResponseDto(user.getId());
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(userConverter.toUserPreviewResponseDTO(user)).thenReturn(dto);

        UserPreviewResponseDTO result = service.getUserByUsername(user.getUsername(), null, null);

        assertEquals(dto, result);
        verify(userRepository).findByUsername(user.getUsername());
    }
}
