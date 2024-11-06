package com.example.exchange_rate.dto.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "exchange_rate")
@Getter
@Setter
@NoArgsConstructor
public class ExchangeRateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="origin_currency")
    private String originCurrency;

    @Column(name="exchange_currency")
    private String exchangeCurrency;

    @Column(name="exchange_amount")
    private BigDecimal exchangeAmount;
}
