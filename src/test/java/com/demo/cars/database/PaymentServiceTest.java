package com.demo.cars.database;

import com.demo.cars.dto.PaymentDto;
import com.demo.cars.entity.Payment;
import com.demo.cars.entity.User;
import com.demo.cars.exception.PaymentNotFoundException;
import com.demo.cars.mapper.PaymentMapperImpl;
import com.demo.cars.model.payment.PaymentRequest;
import com.demo.cars.model.payment.PaymentUpdateRequest;
import com.demo.cars.repository.PaymentRepository;
import com.demo.cars.service.impl.PaymentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    @Mock
    private PaymentRepository repository;
    @Mock
    private PaymentMapperImpl mapper;
    @InjectMocks
    private PaymentServiceImpl service;

    @BeforeEach
    void resetMocks() {
        Mockito.reset(repository);
        Mockito.reset(mapper);
    }

    @Test
    void testFindAll() {
        // arrange
        var user = new User(
                1L,
                "Petya",
                "Sidorov",
                "sidorov@innowise.com",
                "+375293455612",
                Timestamp.from(Instant.now()),
                "JASO1921AS",
                "JI1O90KA1"
        );
        var payment = new Payment(
                1L,
                user,
                1234L,
                "DONE",
                "http://localhost",
                100,
                "one-time",
                Timestamp.from(Instant.now())
        );
        var paymentDto = new PaymentDto(
                1L,
                1234L,
                "DONE",
                "http://localhost",
                100,
                "one-time"
        );

        // act
        when(repository.findAll())
                .thenReturn(List.of(payment));
        when(mapper.entityToDto(List.of(payment)))
                .thenReturn(List.of(paymentDto));
        var payments = service.getAllPayments();

        // assert
        assertEquals(1, payments.size());
        assertEquals(1234L, payments.getFirst().getSessionId());
    }

    @Test
    void testAddPayment() {
        // arrange
        var user = new User(
                1L,
                "Petya",
                "Sidorov",
                "sidorov@innowise.com",
                "+375293455612",
                Timestamp.from(Instant.now()),
                "JASO1921AS",
                "JI1O90KA1"
        );
        var payment = new Payment(
                1L,
                user,
                1234L,
                "DONE",
                "http://localhost",
                100,
                "one-time",
                Timestamp.from(Instant.now())
        );
        var paymentReq = new PaymentRequest(
                1L,
                1234L,
                "DONE",
                "http://localhost",
                100,
                "one-time"
        );
        var paymentDto = new PaymentDto(
                1L,
                1234L,
                "DONE",
                "http://localhost",
                100,
                "one-time"
        );

        // act
        when(mapper.requestToEntity(paymentReq))
                .thenReturn(payment);
        when(repository.save(payment))
                .thenReturn(payment);
        when(mapper.entityToDto(payment))
                .thenReturn(paymentDto);
        var res = service.addPayment(paymentReq);

        // assert
        assertEquals(paymentDto, res);
    }

    @Test
    void testGetPaymentsByUserId() {
        // arrange
        var user = new User(
                1L,
                "Petya",
                "Sidorov",
                "sidorov@innowise.com",
                "+375293455612",
                Timestamp.from(Instant.now()),
                "JASO1921AS",
                "JI1O90KA1"
        );
        var payment = new Payment(
                1L,
                user,
                1234L,
                "DONE",
                "http://localhost",
                100,
                "one-time",
                Timestamp.from(Instant.now())
        );
        var paymentDto = new PaymentDto(
                1L,
                1234L,
                "DONE",
                "http://localhost",
                100,
                "one-time"
        );

        // act
        when(repository.findByUserId(1L))
                .thenReturn(List.of(payment));
        when(mapper.entityToDto(List.of(payment)))
                .thenReturn(List.of(paymentDto));
        var res = service.getPaymentsByUserId(1L);

        // assert
        assertEquals(List.of(paymentDto), res);
    }

    @Test
    void testGetPaymentById() {
        // arrange
        var user = new User(
                1L,
                "Petya",
                "Sidorov",
                "sidorov@innowise.com",
                "+375293455612",
                Timestamp.from(Instant.now()),
                "JASO1921AS",
                "JI1O90KA1"
        );
        var payment = new Payment(
                1L,
                user,
                1234L,
                "DONE",
                "http://localhost",
                100,
                "one-time",
                Timestamp.from(Instant.now())
        );
        var paymentDto = new PaymentDto(
                1L,
                1234L,
                "DONE",
                "http://localhost",
                100,
                "one-time"
        );

        // act
        when(mapper.entityToDto(payment))
                .thenReturn(paymentDto);
        when(repository.findById(1L))
                .thenReturn(Optional.of(payment));
        var res = service.getPaymentById(1L);

        // assert
        assertEquals(paymentDto, res);
    }

    @Test
    void testGetPaymentByIdExc() {
        // act
        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        // assert
        assertThrows(PaymentNotFoundException.class, () -> service.getPaymentById(1L));
    }

    @Test
    void testDeletePayment() {
        // act
        when(repository.existsById(1L))
                .thenReturn(true);

        // assert
        assertDoesNotThrow(() -> service.deletePayment(1L));
    }

    @Test
    void testUpdatePayment() {
        // arrange
        var user = new User(
                1L,
                "Petya",
                "Sidorov",
                "sidorov@innowise.com",
                "+375293455612",
                Timestamp.from(Instant.now()),
                "JASO1921AS",
                "JI1O90KA1"
        );
        var payment = new Payment(
                1L,
                user,
                1234L,
                "DONE",
                "http://localhost",
                100,
                "one-time",
                Timestamp.from(Instant.now())
        );
        var paymentReq = new PaymentUpdateRequest(
                "INCOMPLETE",
                "http://localhost",
                200,
                "one-time"
        );
        var paymentDto = new PaymentDto(
                1L,
                1234L,
                "INCOMPLETE",
                "http://localhost",
                200,
                "one-time"
        );

        // act
        when(repository.findById(1L))
                .thenReturn(Optional.of(payment));
        when(repository.save(payment))
                .thenReturn(payment);
        when(mapper.entityToDto(payment))
                .thenReturn(paymentDto);
        var res = service.updatePayment(1L, paymentReq);

        // assert
        assertEquals(paymentDto, res);
    }

    @Test
    void testUpdatePaymentExc() {
        // arrange
        var updateReq = new PaymentUpdateRequest("", "", 0, "");
        // act
        when(repository.findById(1L))
                .thenReturn(Optional.empty());

        // assert
        assertThrows(PaymentNotFoundException.class,
                () -> service.updatePayment(1L, updateReq));
    }

    @Test
    void testDeletePaymentExc() {
        // act
        when(repository.existsById(1L))
                .thenReturn(false);

        // assert
        assertThrows(PaymentNotFoundException.class, () -> service.deletePayment(1L));
    }
}
