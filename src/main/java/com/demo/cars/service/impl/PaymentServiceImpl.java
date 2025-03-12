package com.demo.cars.service.impl;

import com.demo.cars.dto.PaymentDto;
import com.demo.cars.exception.payment.BadPaymentSessionException;
import com.demo.cars.exception.payment.PaymentNotFoundException;
import com.demo.cars.mapper.PaymentMapper;
import com.demo.cars.model.payment.PaymentRequest;
import com.demo.cars.model.payment.PaymentUpdateRequest;
import com.demo.cars.repository.PaymentRepository;
import com.demo.cars.service.PaymentService;
import com.demo.cars.util.enums.Status;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentServiceImpl implements PaymentService {
    PaymentRepository repository;
    PaymentMapper mapper;

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
    public PaymentDto getActivePayment(Long userId) {
        return getPaymentsByUserId(userId).stream()
                .filter(payment -> Status.PENDING.name().equals(payment.getStatus()))
                .findFirst()
                .orElseThrow(PaymentNotFoundException::new);
    }

    @Override
    public PaymentDto confirmSuccess(String sessionId) {
        var payment = repository.findBySessionId(sessionId)
                .orElseThrow(PaymentNotFoundException::new);

        if (payment.getStatus().equals(Status.COMPLETE.name()))
            throw new BadPaymentSessionException();

        payment.setPaymentDate(Timestamp.from(Instant.now()));
        payment.setStatus(Status.COMPLETE.name());

        return mapper.entityToDto(
                repository.save(payment)
        );
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
