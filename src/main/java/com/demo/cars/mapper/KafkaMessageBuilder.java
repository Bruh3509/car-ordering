package com.demo.cars.mapper;

import com.demo.cars.dto.PaymentDto;
import com.demo.cars.kafka.model.MyKafkaMessage;
import com.demo.cars.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KafkaMessageBuilder {
    UserService service;

    public MyKafkaMessage buildKafkaMessage(PaymentDto paymentDto) {
        var userId = paymentDto.getUserId();
        return new MyKafkaMessage(
                userId,
                paymentDto.getUrl(),
                service.getUserById(userId).getEmail()
        );
    }
}
