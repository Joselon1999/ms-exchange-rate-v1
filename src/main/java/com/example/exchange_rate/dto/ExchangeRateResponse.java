package com.example.exchange_rate.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ExchangeRateResponse {

    private String originalCurrency;
    private String exchangeCurrency;
    private double originalAmount;
    private double exchangeAmount;
}
