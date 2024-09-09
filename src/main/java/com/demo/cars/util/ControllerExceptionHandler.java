package com.demo.cars.util;

import com.demo.cars.exception.ClientException;
import com.demo.cars.exception.UniqueRecordException;
import com.demo.cars.exception.UserNotFoundException;
import com.demo.cars.model.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {
    private static final String EXC_TRANSL = "Request caused exception: ";

    @ExceptionHandler({UserNotFoundException.class, UniqueRecordException.class})
    public <T extends ClientException> ResponseEntity<ErrorResponse> handleClientException(T e) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(
                        e.getClass(), EXC_TRANSL + e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInternalServerError(Exception e) {
        return ResponseEntity.internalServerError()
                .body(new ErrorResponse(
                        e.getClass(), EXC_TRANSL + e.getMessage()));
    }
}
