package com.demo.cars.service.impl;

import com.demo.cars.dto.PaymentDto;
import com.demo.cars.exception.PaymentNotFoundException;
import com.demo.cars.mapper.PaymentMapper;
import com.demo.cars.model.PaymentRequest;
import com.demo.cars.repository.PaymentRepository;
import com.demo.cars.service.PaymentService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

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
    public PaymentDto getPaymentById(Long id) {
        return repository.findById(id)
                .map(mapper::entityToDto)
                .orElseThrow(PaymentNotFoundException::new);
    }

    @Override
    public PaymentDto updatePayment(Long id, PaymentRequest request) {
        var payment = repository.findById(id)
                .orElseThrow(PaymentNotFoundException::new);

        // how better to handle the fact that we cannot change userId, just do not use it in request -> create new request for update?
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
