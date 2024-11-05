package com.example.exchange_rate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ExchangeRateRequest {

    private String originalCurrency;
    private String exchangeCurrency;
    private double originalAmount;
}
