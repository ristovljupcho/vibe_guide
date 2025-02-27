package com.vibe_guide.exceptions.handling;

import com.vibe_guide.exceptions.CustomBadRequestException;
import com.vibe_guide.exceptions.CustomNotFoundException;
import com.vibe_guide.exceptions.CustomUnprocessableEntityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CustomNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionMessage> handleCustomNotFoundException(
            CustomNotFoundException customNotFoundException) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(customNotFoundException.getMessage());
        log.error("Custom NOT FOUND exception: {}", customNotFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionMessage);
    }

    @ExceptionHandler(CustomBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionMessage> handleCustomBadRequestException(
            CustomBadRequestException customBadRequestException) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(customBadRequestException.getMessage());
        log.error("Custom BAD REQUEST exception: {}", customBadRequestException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionMessage);
    }

    @ExceptionHandler(CustomUnprocessableEntityException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<ExceptionMessage> handleCustomUnprocessableEntityException(
            CustomUnprocessableEntityException customUnprocessableEntityException) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(customUnprocessableEntityException.getMessage());
        log.error("Unprocessable entity exception: {}", customUnprocessableEntityException.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exceptionMessage);
    }
}
