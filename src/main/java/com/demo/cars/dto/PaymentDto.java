package com.demo.cars.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentDto {
    Long userId;
    Long sessionId; // ? - do we need these field
    String status; // ? - the same
    String url;
    Integer paymentAmount;
    String type;
}
