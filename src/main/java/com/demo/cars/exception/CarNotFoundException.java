package com.demo.cars.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CarNotFoundException extends ClientException {
  public CarNotFoundException(String message) {
    super(message);
  }
}
