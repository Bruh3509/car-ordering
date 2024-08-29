package com.demo.cars.service.impl;

import com.demo.cars.dto.PaymentDto;
import com.demo.cars.exception.MyStripeException;
import com.demo.cars.mapper.PaymentMapper;
import com.demo.cars.model.payment.StripePaymentRequest;
import com.demo.cars.service.BookingService;
import com.demo.cars.service.CarService;
import com.demo.cars.service.PaymentService;
import com.demo.cars.service.StripeService;
import com.demo.cars.util.enums.Status;
import com.demo.cars.util.enums.Type;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StripeServiceImpl implements StripeService {
    @Value("${stripe.test.private-key}")
    String prKey;
    @Value("${stripe.test.payment-url}")
    String paymentUrl;

    final PaymentMapper paymentMapper;
    final PaymentService paymentService;
    final BookingService bookingService;
    final CarService carService;

    @PostConstruct
    private void postConstruct() {
        Stripe.apiKey = prKey;
    }

    @Override
    public PaymentDto getDtoFromSession(StripePaymentRequest request) {
        var session = createSession(request);
        var sessionUrl = session.getUrl();
        var sessionId = session.getId();
        var paymentAmount = session.getAmountTotal();

        PaymentDto paymentDto = new PaymentDto(
                request.userId(),
                sessionId,
                Status.PENDING.name(),
                sessionUrl,
                paymentAmount.intValue(),
                Type.DEFAULT.name()
        );

        return paymentService.addPayment(paymentMapper.dtoToRequest(paymentDto));
    }

    private Session createSession(StripePaymentRequest request) {
        try {
            return Session.create(
                    SessionCreateParams.builder()
                            .setMode(SessionCreateParams.Mode.PAYMENT)
                            .addPaymentMethodType(SessionCreateParams.PaymentMethodType
                                    .valueOf(request.paymentMethodType()))
                            .setSuccessUrl("%s/success?session_id={CHECKOUT_SESSION_ID}"
                                    .formatted(paymentUrl))
                            .setCancelUrl("%s/cancel".formatted(paymentUrl))
                            .setClientReferenceId(request.userId().toString())
                            .addLineItem(
                                    SessionCreateParams.LineItem.builder()
                                            .setPriceData(
                                                    SessionCreateParams.LineItem.PriceData.builder()
                                                            .setCurrency(request.currency())
                                                            .setUnitAmount(countOverallPrice(
                                                                    request.userId())
                                                            )
                                                            .setProductData(
                                                                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                            .setName("Car")
                                                                            .build()
                                                            )
                                                            .build()
                                            )
                                            .setQuantity(1L)
                                            .build()
                            )
                            .build()
            );
        } catch (StripeException e) {
            throw new MyStripeException(e.getMessage());
        }
    }

    private long countOverallPrice(long userId) {
        // total += minutes of ride * fee
        return bookingService.getAllUserRidesByStatus(userId, Status.COMPLETE.name()).stream()
                .mapToLong(bookingDto ->
                        Duration.between(
                                        bookingDto.getStartDate().toInstant(),
                                        bookingDto.getEndDate().toInstant()
                                )
                                .toMinutes()
                                *
                                carService.getCarById(bookingDto.getCarId())
                                        .getDailyFee()
                )
                .sum();
    }
}
