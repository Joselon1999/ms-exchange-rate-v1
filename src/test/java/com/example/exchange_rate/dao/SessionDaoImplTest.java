package com.example.exchange_rate.dao;

import com.example.exchange_rate.dto.domain.ExchangeRate;
import com.example.exchange_rate.util.exception.CustomException;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class SessionDaoImplTest {

    @InjectMocks
    private SessionDaoImpl sessionDao;

    @Mock
    private RedisTemplate<String, ExchangeRate> redisTemplate;

    @Mock
    private ValueOperations<String, ExchangeRate> valueOperations;

    private ExchangeRate exchangeRate;

    @BeforeEach
    public void setUp() {
        exchangeRate = buildExchangeRate();
        Mockito.when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    public void whenSaveReturnsSuccess() {

        Mockito.doNothing().when(valueOperations).set(Mockito.anyString(), Mockito.any(ExchangeRate.class),
                Mockito.eq(5L), Mockito.eq(TimeUnit.MINUTES));

        Completable result = sessionDao.save(exchangeRate);

        result.test().assertComplete();
        Mockito.verify(valueOperations).set(Mockito.anyString(), Mockito.eq(exchangeRate), Mockito.eq(5L), Mockito.eq(TimeUnit.MINUTES));
    }

    @Test
    public void whenSaveReturnsError() {

        Mockito.doNothing().when(valueOperations).set(Mockito.anyString(), Mockito.any(ExchangeRate.class),
                Mockito.eq(5L), Mockito.eq(TimeUnit.MINUTES));

        Completable result = sessionDao.save(exchangeRate);

        result.test().assertComplete();
        Mockito.verify(valueOperations).set(Mockito.anyString(), Mockito.eq(exchangeRate), Mockito.eq(5L), Mockito.eq(TimeUnit.MINUTES));
    }

    @Test
    public void whenFindByIdReturnsSuccess() {

        Mockito.when(valueOperations.get(Mockito.anyString())).thenReturn(exchangeRate);

        Maybe<ExchangeRate> result = sessionDao.findById(exchangeRate);

        result.test().assertValue(exchangeRate);
        Mockito.verify(valueOperations).get(Mockito.anyString());
    }

    @Test
    public void whenFindByIdReturnsNotFound() {

        Mockito.when(valueOperations.get(Mockito.anyString())).thenReturn(null);

        Maybe<ExchangeRate> result = sessionDao.findById(exchangeRate);

        result.test().assertNoValues();
        Mockito.verify(valueOperations).get(Mockito.anyString());
    }

    @Test
    public void whenFindByIdReturnsError() {

        Mockito.when(valueOperations.get(Mockito.anyString())).thenThrow(new RuntimeException("Redis error"));

        Maybe<ExchangeRate> result = sessionDao.findById(exchangeRate);
        result.test().assertError(RuntimeException.class);
        Mockito.verify(valueOperations).get(Mockito.anyString());
    }
    
    private ExchangeRate buildExchangeRate() {
        return ExchangeRate.builder()
                .exchangeAmount(BigDecimal.valueOf(2))
                .originalAmount(BigDecimal.valueOf(1))
                .exchangeRate(BigDecimal.valueOf(2))
                .exchangeCurrency("PEN")
                .originalCurrency("USD")
                .build();
    }
}