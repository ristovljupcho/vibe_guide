package com.vibe_guide.utils;

import com.vibe_guide.dtos.UserPreviewResponseDTO;
import com.vibe_guide.entities.User;
import com.vibe_guide.enums.Role;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@UtilityClass
public class UserTestData {

    private static final Sort USER_SORT = Sort.by(Sort.Direction.ASC, "username");

    public static final UUID USER_ID = UUID.fromString("062637f0-457c-459a-af9d-cdd80b6c0a55");
    public static final String USERNAME = "test12";
    public static final String NAME = "New User";
    public static final String EMAIL = "test@example.com";
    public static final String PASSWORD = "test1234";
    public static final Role ROLE = Role.USER;
    public static final LocalDateTime CREATED_AT = LocalDateTime.of(2025, 4, 24, 22, 25, 50, 864090);

    public static final PageRequest USER_PAGE_REQUEST =
            PageRequest.of(SharedTestData.PAGE, SharedTestData.SIZE, USER_SORT);

    public static User getUser() {
        User user = new User();
        user.setId(USER_ID);
        user.setUsername(USERNAME);
        user.setName(NAME);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);
        user.setRole(ROLE);
        user.setCreatedAt(CREATED_AT);
        user.setUpdatedAt(CREATED_AT);
        user.setPlaces(Set.of());
        return user;
    }

    public static List<User> getUsers() {
        return List.of(getUser());
    }

    public static Page<User> getPageUsers() {
        return new PageImpl<>(getUsers());
    }

    public static UserPreviewResponseDTO getUserPreviewResponseDTO() {
        return new UserPreviewResponseDTO(USER_ID, USERNAME, NAME, EMAIL, ROLE);
    }

    public static Page<UserPreviewResponseDTO> getPageUserPreviewResponseDTOs() {
        UserPreviewResponseDTO user1 = new UserPreviewResponseDTO(
                UUID.fromString("062637f0-457c-459a-af9d-cdd80b6c0a55"),
                "test12",
                "New User",
                "test@example.com",
                Role.USER
        );

        UserPreviewResponseDTO user2 = new UserPreviewResponseDTO(
                UUID.fromString("b2f7dc12-54e9-472d-9b77-2b53c29f0c3c"),
                "anotherUser",
                "Another User",
                "another@example.com",
                Role.USER
        );

        UserPreviewResponseDTO user3 = new UserPreviewResponseDTO(
                UUID.fromString("b2c1bc33-8908-414b-9e16-f3a5ad4ec41b"),
                "test125",
                "New User 5",
                "test5@example.com",
                Role.USER
        );

        List<UserPreviewResponseDTO> users = List.of(user1, user2, user3);

        return new PageImpl<>(users);
    }

}
