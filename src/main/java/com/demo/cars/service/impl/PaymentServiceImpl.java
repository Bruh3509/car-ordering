package com.demo.cars.service.impl;

import com.demo.cars.dto.PaymentDto;
import com.demo.cars.exception.MyStripeException;
import com.demo.cars.exception.PaymentNotFoundException;
import com.demo.cars.mapper.PaymentMapper;
import com.demo.cars.model.payment.PaymentRequest;
import com.demo.cars.model.payment.PaymentUpdateRequest;
import com.demo.cars.repository.PaymentRepository;
import com.demo.cars.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentServiceImpl implements PaymentService {
    @Value("${stripe.test.private-key}")
    String prKey;
    final PaymentRepository repository;
    final PaymentMapper mapper;

    @PostConstruct
    private void postConstruct() {
        Stripe.apiKey = prKey;
    }

    @Override
    public PaymentIntent createPaymentIntent(PaymentRequest request) {
        var paymentMethodTypes = List.of("card");

        Map<String, Object> params = Map.of(
                "amount", request.paymentAmount(),
                "currency", request.type(),
                "payment_method_types", paymentMethodTypes
        );

        try {
            return PaymentIntent.create(params);
        } catch (StripeException e) {
            throw new MyStripeException(e.getMessage());
        }
    }

    @Override
    public PaymentDto addPayment(PaymentRequest request) {
        return mapper.entityToDto(
                repository.save(
                        mapper.requestToEntity(request)
                )
        );
    }

    @Override
    public List<PaymentDto> getAllPayments() {
        return mapper.entityToDto(
                repository.findAll()
        );
    }

    @Override
    public List<PaymentDto> getPaymentsByUserId(Long userId) {
        return mapper.entityToDto(repository.findByUserId(userId));
    }

    @Override
    public PaymentDto getPaymentById(Long id) {
        return repository.findById(id)
                .map(mapper::entityToDto)
                .orElseThrow(PaymentNotFoundException::new);
    }

    @Override
    public PaymentDto updatePayment(Long id, PaymentUpdateRequest request) {
        var payment = repository.findById(id)
                .orElseThrow(PaymentNotFoundException::new);

        payment.setStatus(request.status());
        payment.setUrl(request.url());
        payment.setPaymentAmount(request.paymentAmount());
        payment.setType(request.type());

        return mapper.entityToDto(repository.save(payment));
    }

    @Override
    public void deletePayment(Long id) {
        if (!repository.existsById(id))
            throw new PaymentNotFoundException();

        repository.deleteById(id);
    }
}
