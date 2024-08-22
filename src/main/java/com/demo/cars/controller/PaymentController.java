package com.demo.cars.controller;

import com.demo.cars.dto.PaymentDto;
import com.demo.cars.model.payment.PaymentRequest;
import com.demo.cars.model.payment.PaymentUpdateRequest;
import com.demo.cars.service.PaymentService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/payment")
@Tag(name = "payment-controller", description = "managing payments logic")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController {
    PaymentService service;

    @GetMapping(value = "/user-id/{id}", produces = "application/json")
    public ResponseEntity<List<PaymentDto>> getPaymentsInfoByUser(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(service.getPaymentsByUserId(id), HttpStatus.OK);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<PaymentDto>> getAllPaymentsInfo() {
        return new ResponseEntity<>(service.getAllPayments(), HttpStatus.OK);
    }

    @GetMapping(value = "/payment-id/{id}", produces = "application/json")
    public ResponseEntity<PaymentDto> getPaymentInfo(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(service.getPaymentById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/add", produces = "application/json", consumes = "application/json")
    public ResponseEntity<PaymentDto> addNewPayment(
            @RequestBody
            @Parameter(name = "new payment post request", required = true)
            PaymentRequest request
    ) {
        return new ResponseEntity<>(service.addPayment(request), HttpStatus.CREATED);
    }

    @PatchMapping(value = "/update/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<PaymentDto> updatePaymentInfo(
            @PathVariable
            Long id,
            @RequestBody
            @Parameter(name = "update payment info request", required = true)
            PaymentUpdateRequest request
    ) {
        return new ResponseEntity<>(service.updatePayment(id, request), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletePayment(
            @PathVariable Long id
    ) {
        service.deletePayment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
