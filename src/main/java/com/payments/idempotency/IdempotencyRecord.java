package com.payments.idempotency;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "IDEMPOTENCY_RECORD")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IdempotencyRecord {

    @Id
    @Column(name = "IDEMPOTENCY_KEY")
    private String idempotencyKey;

    @Column(name = "RESPONSE", columnDefinition = "TEXT")
    private String response;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
}
