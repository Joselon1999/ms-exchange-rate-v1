package com.example.exchange_rate.dao;

import com.example.exchange_rate.dto.entity.ExchangeRateEntity;
import com.example.exchange_rate.repository.ExchangeRateRepository;
import com.example.exchange_rate.util.exception.CustomException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.reactivex.rxjava3.core.Single;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.exchange_rate.util.constants.Constants.*;

@Service
@Slf4j
public class ExchangeRateDaoImpl implements ExchangeRateDao{

    @Autowired
    private ExchangeRateRepository repository;

    @Override
    @CircuitBreaker(name = "CircuitBreakerService")
    public Single<ExchangeRateEntity> getExchangeRate(String originalCurrency, String exchangeCurrency) {
        return Single.fromCallable(() -> repository.findByOriginCurrencyAndExchangeCurrency(originalCurrency, exchangeCurrency)
                        .orElseThrow(() -> new CustomException(COD002, MES002, STA002)))
                .doOnSuccess(r -> log.info("Success on ExchangeRateDaoImpl.getExchangeRate"))
                .doOnError(th -> log.error("Error on ExchangeRateDaoImpl.getExchangeRate", th));
    }
}
