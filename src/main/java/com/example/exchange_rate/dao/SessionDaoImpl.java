package com.example.exchange_rate.dao;

import com.example.exchange_rate.dto.ExchangeRateRequest;
import com.example.exchange_rate.dto.ExchangeRateResponse;
import com.example.exchange_rate.dto.domain.ExchangeRate;
import com.example.exchange_rate.util.constants.Constants;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class SessionDaoImpl implements SessionDao{

    @Autowired
    private RedisTemplate<String, ExchangeRate> redisTemplate;

    @Override
    public Completable save(ExchangeRate exchangeRate) {
        return Completable.fromCallable(() -> {
            redisTemplate.opsForValue().set(
                    generateKey(exchangeRate.getOriginalCurrency(), exchangeRate.getExchangeCurrency()),
                    exchangeRate,
                    5,
                    TimeUnit.MINUTES
            );
            return exchangeRate;
        })
                .doOnComplete(() -> log.info("Success on SessionDaoImpl.save"))
                .doOnError(th -> log.error("Error on SessionDaoImpl.save",th));
    }
    @Override
    public Maybe<ExchangeRate> findById(ExchangeRate request) {
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
                .concat(Optional.of(originalCurrency).orElse(""))
                .concat(Constants.TO)
                .concat(Optional.of(exchangeCurrency).orElse(""));
    }
}
