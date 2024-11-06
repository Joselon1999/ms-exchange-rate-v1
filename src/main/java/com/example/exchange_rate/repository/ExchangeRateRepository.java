package com.example.exchange_rate.repository;

import com.example.exchange_rate.dto.entity.ExchangeRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRateEntity,Long> {

    ExchangeRateEntity findByOriginCurrencyAndExchangeCurrency(String originCurrency, String exchangeCurrency);
}
