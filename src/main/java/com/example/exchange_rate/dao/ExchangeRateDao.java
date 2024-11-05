package com.example.exchange_rate.dao;

import com.example.exchange_rate.dto.entity.ExchangeRate;
import io.reactivex.rxjava3.core.Single;

import java.util.List;

public interface ExchangeRateDao {

    Single<ExchangeRate> getExchangeRate(String originalCurrency, String exchangeCurrency);

    Single<List<ExchangeRate>> listExchangeRate();
}
