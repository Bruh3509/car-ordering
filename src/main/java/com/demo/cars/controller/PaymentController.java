package com.demo.cars.controller;

import com.demo.cars.dto.PaymentDto;
import com.demo.cars.kafka.KafkaProducer;
import com.demo.cars.mapper.KafkaMessageBuilder;
import com.demo.cars.model.ErrorResponse;
import com.demo.cars.model.payment.PaymentUpdateRequest;
import com.demo.cars.model.payment.StripePaymentRequest;
import com.demo.cars.service.BookingService;
import com.demo.cars.service.PaymentService;
import com.demo.cars.service.StripeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/payment")
@Tag(name = "payment-controller", description = "managing payments logic")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Bad Request",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                        mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "Internal Server Error",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class),
                        mediaType = "application/json"))
})
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController {
    PaymentService paymentService;
    BookingService bookingService;
    StripeService stripeService;
    KafkaMessageBuilder builder;
    KafkaProducer kafkaProducer;

    @GetMapping(value = "/user-id/{id}", produces = "application/json")
    @Operation(summary = "get user payments", description = "lists all user's payments",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PaymentDto.class)),
                            mediaType = "application/json")))
    public ResponseEntity<List<PaymentDto>> getPaymentsInfoByUser(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(paymentService.getPaymentsByUserId(id), HttpStatus.OK);
    }

    @GetMapping(produces = "application/json")
    @Operation(summary = "get all payments", description = "lists all payment that have been done",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PaymentDto.class)),
                            mediaType = "application/json")))
    public ResponseEntity<List<PaymentDto>> getAllPaymentsInfo() {
        return new ResponseEntity<>(paymentService.getAllPayments(), HttpStatus.OK);
    }

    @GetMapping(value = "/payment-id/{id}", produces = "application/json")
    @Operation(summary = "get payment", description = "retrieves payment by id",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = PaymentDto.class),
                            mediaType = "application/json")))
    public ResponseEntity<PaymentDto> getPaymentInfo(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(paymentService.getPaymentById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/success")
    public ResponseEntity<String> paymentSuccess(@RequestParam("session_id") String sessionId) {
        var payment = paymentService.confirmSuccess(sessionId);
        kafkaProducer.sendPaymentUpdate(builder.buildKafkaMessage(payment));
        bookingService.updateRidesStatus(payment.getUserId());
        return new ResponseEntity<>("Payment succeeded.\n", HttpStatus.OK);
    }

    @GetMapping(value = "/cancel")
    public ResponseEntity<String> paymentCancel() {
        return new ResponseEntity<>("Payment has been canceled.", HttpStatus.OK);
    }

    @PostMapping(value = "/add", consumes = "application/json")
    @Operation(summary = "add payment", description = "creates new Stripe payment session",
            responses = @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(schema = @Schema(implementation = PaymentDto.class),
                            mediaType = "application/json")))
    public ResponseEntity<String> addNewPayment(@RequestBody StripePaymentRequest request) {
        var dto = stripeService.getDtoFromSession(request);
        kafkaProducer.sendPaymentCreate(builder.buildKafkaMessage(dto));
        return new ResponseEntity<>(dto.getUrl(),
                HttpStatus.CREATED);
    }

    @PatchMapping(value = "/update/{id}", produces = "application/json", consumes = "application/json")
    @Operation(summary = "update payment", description = "updates payment status, date, etc.",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = PaymentDto.class),
                            mediaType = "application/json")))
    public ResponseEntity<PaymentDto> updatePaymentInfo(
            @PathVariable Long id,
            @RequestBody PaymentUpdateRequest request
    ) {
        return new ResponseEntity<>(paymentService.updatePayment(id, request), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "delete payment", description = "deletes payment",
            responses = @ApiResponse(responseCode = "204", description = "No Content"))
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
