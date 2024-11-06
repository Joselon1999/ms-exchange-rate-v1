package com.example.exchange_rate.dto.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRate {

    private String originalCurrency;
    private String exchangeCurrency;
    private BigDecimal originalAmount;
    private BigDecimal exchangeAmount;
    private BigDecimal exchangeRate;
}
