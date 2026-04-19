package com.payments;

import com.payments.transaction.infrastructure.PaymentTransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;


@SpringBootTest(
        properties = {
                "spring.autoconfigure.exclude=" +
                        "org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration," +
                        "org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration," +
                        "org.springframework.boot.jdbc.autoconfigure.DataSourceTransactionManagerAutoConfiguration," +
                        "org.springframework.boot.kafka.autoconfigure.KafkaAutoConfiguration," +
                        "org.springframework.data.jpa.repository.config.JpaRepositoriesAutoConfiguration"
        }
)
class PaymentServiceApplicationTests {

    @MockitoBean
    PaymentTransactionRepository paymentTransactionRepository;


    @Test
    void contextLoads() {
    }
}