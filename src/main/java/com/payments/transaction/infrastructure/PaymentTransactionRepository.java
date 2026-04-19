package com.payments.transaction.infrastructure;

import com.payments.transaction.domain.PaymentStatus;
import com.payments.transaction.domain.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {

    List<PaymentTransaction> findByStatus(PaymentStatus status);

    Optional<PaymentTransaction> findBySenderIdAndStatus(Long senderId, PaymentStatus status);
}
