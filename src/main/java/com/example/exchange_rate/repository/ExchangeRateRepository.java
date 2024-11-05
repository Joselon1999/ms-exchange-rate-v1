package com.example.exchange_rate.repository;

import com.example.exchange_rate.dto.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate,Long> {

    ExchangeRate findByMonedaOrigenAndMonedaDestino(String monedaOrigen, String monedaDestino);
}
