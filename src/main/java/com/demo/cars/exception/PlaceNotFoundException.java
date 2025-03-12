package com.demo.cars.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PlaceNotFoundException extends ClientException {
    public PlaceNotFoundException(String message) {
        super(message);
    }
}
