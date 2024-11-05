package com.example.exchange_rate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ExchangeRateResponse {

    private String originalCurrency;
    private String exchangeCurrency;
    private double originalAmount;
    private double exchangeAmount;
}
