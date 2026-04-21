package com.payments.transaction.infrastructure;

import com.payments.transaction.domain.PaymentCreatedEvent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class PaymentEventProducer {


    private final KafkaTemplate<String, PaymentCreatedEvent> kafkaTemplate;

    @Value("${spring.kafka.topic}")
    private String kafkaTopic;

    PaymentEventProducer(KafkaTemplate<String, PaymentCreatedEvent> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPaymentCreatedEvent (PaymentCreatedEvent event){
        kafkaTemplate.send(kafkaTopic, event);
        log.info("Payment event sent for paymentId: {}", event.getPaymentId());
    }

}
