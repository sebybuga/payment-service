package com.payments;

import com.payments.transaction.infrastructure.PaymentEventConsumer;
import com.payments.transaction.infrastructure.PaymentEventProducer;
import com.payments.transaction.infrastructure.PaymentTransactionRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;


@SpringBootTest
class PaymentServiceApplicationTests {

    @MockitoBean
    PaymentTransactionRepository paymentTransactionRepository;

    @MockitoBean
    PaymentEventProducer paymentEventProducer;

    @MockitoBean
    PaymentEventConsumer paymentEventConsumer;

    @MockitoBean
    ModelMapper modelMapper;

    @Test
    void contextLoads() {
        // Verifies that the Spring application context loads successfully
    }
}