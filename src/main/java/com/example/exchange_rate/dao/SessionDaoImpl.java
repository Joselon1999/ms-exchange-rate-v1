package com.example.exchange_rate.dao;

import com.example.exchange_rate.dto.domain.ExchangeRate;
import com.example.exchange_rate.util.constants.Constants;
import com.example.exchange_rate.util.exception.CustomException;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.example.exchange_rate.util.constants.Constants.*;

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
                    TimeUnit.MINUTES);
            return exchangeRate;
        })
                .onErrorResumeNext(th -> Completable.error(new CustomException(COD001, MES001, STA001)))
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
