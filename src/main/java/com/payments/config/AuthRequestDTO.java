package com.payments.config;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDTO {
    @NotNull
    private String username;
    @NotNull
    private String password;
}