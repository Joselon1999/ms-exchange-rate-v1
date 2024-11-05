package com.example.exchange_rate.dao;

import com.example.exchange_rate.dto.ExchangeRateRequest;
import com.example.exchange_rate.dto.ExchangeRateResponse;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

public interface SessionDao {

    Single<ExchangeRateResponse> save(ExchangeRateResponse exchangeRateResponse);

    Maybe<ExchangeRateResponse> findById(ExchangeRateRequest request);
}
