package com.example.exchange_rate.dao;

import com.example.exchange_rate.dto.ExchangeRateRequest;
import com.example.exchange_rate.dto.ExchangeRateResponse;
import com.example.exchange_rate.util.constants.Constants;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class SessionDaoImpl implements SessionDao{

    @Autowired
    private RedisTemplate<String, ExchangeRateResponse> redisTemplate;

    @Override
    public Single<ExchangeRateResponse> save(ExchangeRateResponse exchangeRateResponse) {
        return Single.fromCallable(() -> {
            redisTemplate.opsForValue().set(
                    generateKey(exchangeRateResponse.getOriginalCurrency(), exchangeRateResponse.getExchangeCurrency()),
                    exchangeRateResponse,
                    5,
                    TimeUnit.MINUTES
            );
            return exchangeRateResponse;
        })
                .doOnSuccess(r -> log.info("Success on SessionDaoImpl.save"))
                .doOnError(th -> log.error("Error on SessionDaoImpl.save",th));
    }

    @Override
    public Maybe<ExchangeRateResponse> findById(ExchangeRateRequest request) {
        return Maybe.fromCallable(() -> redisTemplate.opsForValue()
                        .get(generateKey(request.getOriginalCurrency(),
                                request.getExchangeCurrency())))
                .filter(Objects::nonNull)
                .switchIfEmpty(Maybe.empty())
                .doOnSuccess(r -> log.info("Success on SessionDaoImpl.findById"))
                .doOnError(th -> log.error("Error on SessionDaoImpl.findById",th));
    }

    private String generateKey(String originalCurrency, String exchangeCurrency) {
        return Constants.REDIS
                .concat(originalCurrency)
                .concat(Constants.TO)
                .concat(exchangeCurrency);
    }
}
