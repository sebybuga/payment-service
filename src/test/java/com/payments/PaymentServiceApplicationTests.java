package com.payments;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


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

    @Test
    void contextLoads() {
    }
}