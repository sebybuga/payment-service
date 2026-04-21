package com.payments.transaction.infrastructure;


import com.payments.transaction.domain.PaymentCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentEventConsumer {

    @KafkaListener(topics = "${spring.kafka.topic}", groupId = "${spring.kafka.group-id}")
    public void consumeMessage(PaymentCreatedEvent event){
        log.info("Payment event received - paymentId: {}, amount: {}, status: {}",
                event.getPaymentId(),
                event.getAmount(),
                event.getStatus());
    }
}
