package com.example.exchange_rate.dao;

import com.example.exchange_rate.dto.entity.ExchangeRateEntity;
import io.reactivex.rxjava3.core.Single;

public interface ExchangeRateDao {

    Single<ExchangeRateEntity> getExchangeRate(String originalCurrency, String exchangeCurrency);
}
