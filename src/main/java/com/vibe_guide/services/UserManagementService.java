package com.vibe_guide.services;

import com.vibe_guide.dtos.UserChangePasswordRequestDTO;
import com.vibe_guide.dtos.UserLoginRequestDTO;
import com.vibe_guide.dtos.UserRegisterRequestDTO;

import java.util.UUID;

public interface UserManagementService {

    String userRegister(UserRegisterRequestDTO registerRequestDTO);

    String userLogin(UserLoginRequestDTO userLoginRequestDTO);

    String userLogout(UUID userId);

    String changePassword(UserChangePasswordRequestDTO changePasswordRequestDTO);

    String deleteUser(UUID userId, String password);
}
