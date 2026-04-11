package com.vibe_guide.user.services;

import com.vibe_guide.user.dtos.UserChangePasswordRequestDTO;
import com.vibe_guide.user.dtos.UserLoginRequestDTO;
import com.vibe_guide.user.dtos.UserRegisterRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.UUID;

public interface UserManagementService {

    String userRegister(UserRegisterRequestDTO registerRequestDTO);

    String userLogin(UserLoginRequestDTO userLoginRequestDTO, HttpServletRequest request, HttpServletResponse response);

    String userLogout(UUID userId);

    String changePassword(UserChangePasswordRequestDTO changePasswordRequestDTO);

    String deleteUser(UUID userId, String password);
}
