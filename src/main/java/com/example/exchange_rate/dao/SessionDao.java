package com.example.exchange_rate.dao;

import com.example.exchange_rate.dto.ExchangeRateRequest;
import com.example.exchange_rate.dto.ExchangeRateResponse;
import com.example.exchange_rate.dto.domain.ExchangeRate;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

public interface SessionDao {

    Completable save(ExchangeRate exchangeRatee);

    Maybe<ExchangeRate> findById(ExchangeRate request);
}
