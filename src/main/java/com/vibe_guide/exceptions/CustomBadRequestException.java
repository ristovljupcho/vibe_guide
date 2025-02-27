package com.vibe_guide.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomBadRequestException extends RuntimeException {
    private final String message;
}
