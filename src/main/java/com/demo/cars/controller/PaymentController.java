package com.demo.cars.controller;

import com.demo.cars.dto.PaymentDto;
import com.demo.cars.model.payment.PaymentUpdateRequest;
import com.demo.cars.model.payment.StripePaymentRequest;
import com.demo.cars.service.PaymentService;
import com.demo.cars.service.StripeService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController {
    PaymentService paymentService;
    StripeService stripeService;

    @GetMapping(value = "/user-id/{id}", produces = "application/json")
    public ResponseEntity<List<PaymentDto>> getPaymentsInfoByUser(@PathVariable Long id) {
        return new ResponseEntity<>(paymentService.getPaymentsByUserId(id), HttpStatus.OK);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<PaymentDto>> getAllPaymentsInfo() {
        return new ResponseEntity<>(paymentService.getAllPayments(), HttpStatus.OK);
    }

    @GetMapping(value = "/payment-id/{id}", produces = "application/json")
    public ResponseEntity<PaymentDto> getPaymentInfo(@PathVariable Long id) {
        return new ResponseEntity<>(paymentService.getPaymentById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/success")
    public ResponseEntity<String> paymentSuccess(@RequestParam("session_id") String sessionId) {
        var res = paymentService.confirmSuccess(sessionId);
        return new ResponseEntity<>("Payment succeeded.\n" + res.toString(), HttpStatus.OK);
    }

    @GetMapping(value = "/cancel")
    public ResponseEntity<String> paymentCancel() {
        return new ResponseEntity<>("Payment has been canceled.", HttpStatus.OK);
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<String> addNewPayment(@RequestBody StripePaymentRequest request) {
        return new ResponseEntity<>(stripeService.getDtoFromSession(request).getUrl(),
                HttpStatus.CREATED);
    }

    @PatchMapping(value = "/update/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<PaymentDto> updatePaymentInfo(
            @PathVariable Long id,
            @RequestBody PaymentUpdateRequest request
    ) {
        return new ResponseEntity<>(paymentService.updatePayment(id, request), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
