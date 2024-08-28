package com.demo.cars.service;

import com.demo.cars.dto.PaymentDto;
import com.demo.cars.model.payment.StripePaymentRequest;

public interface StripeService {
    PaymentDto getDtoFromSession(StripePaymentRequest request);
}
