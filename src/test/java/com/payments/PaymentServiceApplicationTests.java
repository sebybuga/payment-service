package com.payments;

import com.payments.transaction.infrastructure.PaymentTransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;


@SpringBootTest
class PaymentServiceApplicationTests {

    @MockitoBean
    PaymentTransactionRepository paymentTransactionRepository;


    @Test
    void contextLoads() {
    }
}