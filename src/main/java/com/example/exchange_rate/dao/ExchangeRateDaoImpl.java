package com.example.exchange_rate.dao;

import com.example.exchange_rate.dto.entity.ExchangeRate;
import com.example.exchange_rate.repository.ExchangeRateRepository;
import io.reactivex.rxjava3.core.Single;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ExchangeRateDaoImpl implements ExchangeRateDao{

    @Autowired
    private ExchangeRateRepository repository;

    @Override
    public Single<ExchangeRate> getExchangeRate(String originalCurrency, String exchangeCurrency) {
        return Single.just(repository.findByMonedaOrigenAndMonedaDestino(originalCurrency,exchangeCurrency))
                .doOnSuccess(r -> log.info("Success on ExchangeRateDaoImpl.getExchangeRate"))
                .doOnError(th -> log.error("Error on ExchangeRateDaoImpl.getExchangeRate",th));
    }
}
