package com.example.exchange_rate.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ExchangeRateResponse {

    private String originalCurrency;
    private String exchangeCurrency;
    private BigDecimal originalAmount;
    private BigDecimal exchangeAmount;
    private BigDecimal exchangeRate;
}
