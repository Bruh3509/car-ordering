package com.demo.cars.service;

import com.demo.cars.dto.PaymentDto;
import com.demo.cars.model.payment.PaymentRequest;
import com.demo.cars.model.payment.PaymentUpdateRequest;

import java.util.List;

public interface PaymentService {
    PaymentDto addPayment(PaymentRequest request);

    List<PaymentDto> getAllPayments();

    List<PaymentDto> getPaymentsByUserId(Long userId);

    PaymentDto getPaymentById(Long id);

    PaymentDto updatePayment(Long id, PaymentUpdateRequest request);

    void deletePayment(Long id);
}
