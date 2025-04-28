package com.vibe_guide.services.impl;

import com.vibe_guide.converters.UserConverter;
import com.vibe_guide.dtos.UserPreviewResponseDTO;
import com.vibe_guide.enums.Role;
import com.vibe_guide.enums.sorting.SortDirection;
import com.vibe_guide.enums.sorting.UserSortBy;
import com.vibe_guide.exceptions.UserNotFoundException;
import com.vibe_guide.repositories.UserRepository;
import com.vibe_guide.utils.SharedTestData;
import com.vibe_guide.utils.UserTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserQueryServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserConverter userConverter;

    @InjectMocks
    private UserQueryServiceImpl userQueryService;

    @Test
    void getPaginatedUsers_shouldReturnPageOfUserPreviewResponseDTO() {
        when(userRepository.findAll(UserTestData.USER_PAGE_REQUEST))
                .thenReturn(UserTestData.getPageUsers());
        when(userConverter.toUserPreviewResponseDTO(UserTestData.getUser()))
                .thenReturn(UserTestData.getUserPreviewResponseDTO());

        Page<UserPreviewResponseDTO> result = userQueryService.getPaginatedUsers(
                Role.USER, UserSortBy.USERNAME, SortDirection.ASC,
                SharedTestData.PAGE, SharedTestData.SIZE
        );

        assertThat(result).hasSize(1);
        assertThat(result.getContent().get(0).username()).isEqualTo(UserTestData.USERNAME);

        verify(userRepository).findAll(UserTestData.USER_PAGE_REQUEST);
        verify(userConverter).toUserPreviewResponseDTO(UserTestData.getUser());
    }

    @Test
    void getUserById_shouldReturnUserPreviewResponseDTO_whenUserExists() {
        UUID userId = UserTestData.USER_ID;

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(UserTestData.getUser()));
        when(userConverter.toUserPreviewResponseDTO(UserTestData.getUser()))
                .thenReturn(UserTestData.getUserPreviewResponseDTO());

        UserPreviewResponseDTO result = userQueryService.getUserById(userId);

        assertThat(result.username()).isEqualTo(UserTestData.USERNAME);

        verify(userRepository).findById(userId);
        verify(userConverter).toUserPreviewResponseDTO(UserTestData.getUser());
    }

    @Test
    void getUserById_shouldThrowException_whenUserDoesNotExist() {
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userQueryService.getUserById(userId))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining(userId.toString());

        verify(userRepository).findById(userId);
        verifyNoInteractions(userConverter);
    }

    @Test
    void getUserByUsername_shouldReturnUserPreviewResponseDTO_whenUserExists() {
        String username = UserTestData.USERNAME;

        when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(UserTestData.getUser()));
        when(userConverter.toUserPreviewResponseDTO(UserTestData.getUser()))
                .thenReturn(UserTestData.getUserPreviewResponseDTO());

        UserPreviewResponseDTO result = userQueryService.getUserByUsername(username, null, null);

        assertThat(result.username()).isEqualTo(UserTestData.USERNAME);

        verify(userRepository).findByUsername(username);
        verify(userConverter).toUserPreviewResponseDTO(UserTestData.getUser());
    }

    @Test
    void getUserByUsername_shouldThrowException_whenUserDoesNotExist() {
        String username = "nonexistentUser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userQueryService.getUserByUsername(username, null, null))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining(username);

        verify(userRepository).findByUsername(username);
        verifyNoInteractions(userConverter);
    }
    @Test
    void getPageUserPreviewResponseDTOs_shouldContainThreeUsers() {
        Page<UserPreviewResponseDTO> page = UserTestData.getPageUserPreviewResponseDTOs();

        assertThat(page).hasSize(3);
        assertThat(page.getContent()).extracting(UserPreviewResponseDTO::username)
                .containsExactly("test12", "anotherUser", "test125");

        assertThat(page.getContent().get(2).email()).isEqualTo("test5@example.com");
    }

}
