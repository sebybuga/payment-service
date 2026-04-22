package com.payments.transaction.api;

import com.payments.config.AuthRequestDTO;
import com.payments.config.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;

    @Value("${auth.username}")
    private String authUsername;

    @Value("${auth.password}")
    private String authPassword;

    @PostMapping(value = "/token", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> generateToken(@RequestBody @Valid AuthRequestDTO request) {
        //Just for the demo. In production, use a database.
        if (
                !request.getUsername().equals(authUsername) ||
                        !request.getPassword().equals(authPassword)
        ) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid credentials"));
        }

        return ResponseEntity.ok(Map.of("token", jwtService.generateToken(request.getUsername())));
    }

}
