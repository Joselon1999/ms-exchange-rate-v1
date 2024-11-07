package com.example.exchange_rate.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ExchangeRateRequest {

    @Pattern(regexp = "^[A-Z]{3}$", message = "Not a valid currency")
    private String originalCurrency;

    @Pattern(regexp = "^[A-Z]{3}$", message = "Not a valid currency")
    private String exchangeCurrency;

    @Positive(message = "Amount must be positive")
    private BigDecimal originalAmount;
}
