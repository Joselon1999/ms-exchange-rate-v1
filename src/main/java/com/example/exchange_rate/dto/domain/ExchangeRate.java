package com.example.exchange_rate.dto.domain;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRate {

    private String originalCurrency;
    private String exchangeCurrency;
    private BigDecimal originalAmount;
    private BigDecimal exchangeAmount;
    private BigDecimal exchangeRate;
}
