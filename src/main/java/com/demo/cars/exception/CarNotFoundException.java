package com.demo.cars.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CarNotFoundException extends RuntimeException {
  public CarNotFoundException(String message) {
    super(message);
  }
}
