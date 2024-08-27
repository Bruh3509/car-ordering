package com.demo.cars.controller;

import com.demo.cars.dto.PaymentDto;
import com.demo.cars.exception.MyStripeException;
import com.demo.cars.model.payment.PaymentRequest;
import com.demo.cars.model.payment.PaymentUpdateRequest;
import com.demo.cars.service.PaymentService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.demo.cars.util.PropertyUtil.STRIPE_PAYMENT_INTENT_FAIL;
import static com.demo.cars.util.PropertyUtil.STRIPE_PAYMENT_INTENT_SUCCESS;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController {
    PaymentService service;

    @GetMapping(value = "/user-id/{id}", produces = "application/json")
    public ResponseEntity<List<PaymentDto>> getPaymentsInfoByUser(@PathVariable Long id) {
        return new ResponseEntity<>(service.getPaymentsByUserId(id), HttpStatus.OK);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<PaymentDto>> getAllPaymentsInfo() {
        return new ResponseEntity<>(service.getAllPayments(), HttpStatus.OK);
    }

    @GetMapping(value = "/payment-id/{id}", produces = "application/json")
    public ResponseEntity<PaymentDto> getPaymentInfo(@PathVariable Long id) {
        return new ResponseEntity<>(service.getPaymentById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentRequest request) {
        var paymentIntent = service.createPaymentIntent(request);
        service.addPayment(request);
        var paymentJson = paymentIntent.toJson();

        return new ResponseEntity<>(paymentJson, HttpStatus.OK);
    }

    @PostMapping(value = "/webhook")
    public ResponseEntity<String> handleStripeWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader,
            @Value("${stripe.test.webhook-key}") String wbhSec
    ) {
        Event event;
        try {
            event = Webhook.constructEvent(payload, sigHeader, wbhSec);
        } catch (SignatureVerificationException e) {
            throw new MyStripeException(e.getMessage());
        }

        return switch (event.getType()) {
            case STRIPE_PAYMENT_INTENT_SUCCESS -> {
                service.updatePayment(1L, null); // TODO where to get id????
                // TODO or find not by id?
                yield new ResponseEntity<>(event.toJson(),
                        HttpStatus.OK);
            }
            case STRIPE_PAYMENT_INTENT_FAIL -> {
                service.updatePayment(1L, null); // TODO the same as the bottom one
                yield new ResponseEntity<>("Payment failed!%n%s".formatted(event.toJson()),
                        HttpStatus.BAD_REQUEST);
            }
            default -> new ResponseEntity<>(HttpStatus.OK);
        };
    }

    @PostMapping(value = "/add", produces = "application/json", consumes = "application/json")
    public ResponseEntity<PaymentDto> addNewPayment(@RequestBody PaymentRequest request) {
        return new ResponseEntity<>(service.addPayment(request), HttpStatus.CREATED);
    }

    @PatchMapping(value = "/update/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<PaymentDto> updatePaymentInfo(
            @PathVariable Long id,
            @RequestBody PaymentUpdateRequest request
    ) {
        return new ResponseEntity<>(service.updatePayment(id, request), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        service.deletePayment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
