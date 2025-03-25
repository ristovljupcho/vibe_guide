package com.vibe_guide.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserResponseMessages {
    public static final String USER_REGISTER_MESSAGE = "User successfully registered.";
    public static final String USER_LOGIN_MESSAGE = "User successfully logged in.";
    public static final String USER_CHANGE_PASSWORD_MESSAGE = "Password successfully changed.";
    public static final String USER_LOGOUT_MESSAGE = "User successfully logged out.";
    public static final String USER_DELETE_MESSAGE = "User successfully deleted.";
    public static final String USER_PASSWORD_NOT_MATCH = "Password does not match.";
    public static final String USER_PASSWORD_TOO_SHORT = "New password must be at least 8 characters long for user";

}
