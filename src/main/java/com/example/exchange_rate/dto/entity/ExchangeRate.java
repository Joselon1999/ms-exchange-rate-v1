package com.example.exchange_rate.dto.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "exchange_rate")
@Getter
@Setter
@NoArgsConstructor
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="moneda_origen")
    private String monedaOrigen;

    @Column(name="moneda_destino")
    private String monedaDestino;

    @Column(name="monto_tipo_cambio")
    private double montoTipoCambio;
}
