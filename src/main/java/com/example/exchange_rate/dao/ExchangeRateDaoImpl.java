package com.example.exchange_rate.dao;

import com.example.exchange_rate.dto.entity.ExchangeRate;
import com.example.exchange_rate.repository.ExchangeRateRepository;
import io.reactivex.rxjava3.core.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExchangeRateDaoImpl implements ExchangeRateDao{

    @Autowired
    private ExchangeRateRepository repository;

    @Override
    public Single<ExchangeRate> getExchangeRate(String originalCurrency, String exchangeCurrency) {
        return Single.just(repository.findByMonedaOrigenAndMonedaDestino(originalCurrency,exchangeCurrency));
    }

    @Override
    public Single<List<ExchangeRate>> listExchangeRate() {
        return Single.just(repository.findAll());
    }
}
