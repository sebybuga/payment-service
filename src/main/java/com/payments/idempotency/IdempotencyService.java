package com.payments.idempotency;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class IdempotencyService {

    private final IdempotencyRepository idempotencyRepository;

    public boolean existsByKey(String key) {
        return idempotencyRepository.existsById(key);
    }

    public Optional<String> getResponse(String key) {
        return idempotencyRepository.findById(key)
                .map(IdempotencyRecord::getResponse);
    }

    public void saveRecord(String key, String response) {
        IdempotencyRecord idempotencyRecord = IdempotencyRecord.builder()
                .idempotencyKey(key)
                .response(response)
                .createdAt(LocalDateTime.now())
                .build();
        idempotencyRepository.save(idempotencyRecord);
        log.info("Idempotency record saved for key: {}", key);
    }
}