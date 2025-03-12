package com.demo.cars.kafka;

import com.demo.cars.kafka.model.MyKafkaMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KafkaProducer {
    KafkaTemplate<String, MyKafkaMessage> kafkaTemplate;
    static String TOPIC1 = "new-payment"; // maybe replace somewhere
    static String TOPIC2 = "update-payment";

    public void sendPaymentCreate(MyKafkaMessage newPayment) {
        kafkaTemplate.send(TOPIC1, newPayment);
    }

    public void sendPaymentUpdate(MyKafkaMessage updatePayment) {
        kafkaTemplate.send(TOPIC2, updatePayment);
    }
}
