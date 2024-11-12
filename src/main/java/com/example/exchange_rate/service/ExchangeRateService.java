package com.example.exchange_rate.service;

import com.example.exchange_rate.dto.ExchangeRateRequest;
import com.example.exchange_rate.dto.ExchangeRateResponse;
import com.example.exchange_rate.dto.domain.ExchangeRate;
import io.reactivex.rxjava3.core.Single;

public interface ExchangeRateService {

    Single<ExchangeRate> getExchangeRate(ExchangeRate request,String coupon);
}
