package com.example.exchange_rate.repository;

import com.example.exchange_rate.dto.entity.ExchangeRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRateEntity,Long> {

    Optional<ExchangeRateEntity> findByOriginCurrencyAndExchangeCurrency(String originCurrency, String exchangeCurrency);
}
