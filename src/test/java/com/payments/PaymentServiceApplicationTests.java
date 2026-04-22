package com.payments;

import com.payments.idempotency.IdempotencyRepository;
import com.payments.transaction.infrastructure.PaymentEventProducer;
import com.payments.transaction.infrastructure.PaymentTransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
@TestPropertySource(properties = {
        "jwt.secret=test-secret-key-minimum-32-characters-long",
        "jwt.expiration=86400000",
        "auth.username=test",
        "auth.password=test",
        "spring.flyway.enabled=false",
        "spring.autoconfigure.exclude=org.springframework.boot.kafka.autoconfigure.KafkaAutoConfiguration"
})
class PaymentServiceApplicationTests {

    @MockitoBean
    PaymentTransactionRepository paymentTransactionRepository;

    @MockitoBean
    PaymentEventProducer paymentEventProducer;

    @MockitoBean
    IdempotencyRepository idempotencyRepository;

    @Test
    void contextLoads() {
        // Verifies that the Spring application context loads successfully
    }
}