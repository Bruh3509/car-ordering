package com.demo.cars.util;

import com.demo.cars.exception.ClientException;
import com.demo.cars.exception.UniqueRecordException;
import com.demo.cars.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler({UserNotFoundException.class, UniqueRecordException.class})
    public <T extends ClientException> ResponseEntity<String> handleClientException(T e) {
        return ResponseEntity.badRequest()
                .body("Request caused exception: `%s` -> %s"
                        .formatted(e.getClass(), e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleInternalServerError(Exception e) {
        return ResponseEntity.internalServerError()
                .body("Request caused exception: `%s` -> %s"
                        .formatted(e.getClass(), e.getMessage()));
    }
}
