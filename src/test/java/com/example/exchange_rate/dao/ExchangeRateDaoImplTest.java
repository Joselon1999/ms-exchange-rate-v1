package com.example.exchange_rate.dao;

import com.example.exchange_rate.dto.entity.ExchangeRateEntity;
import com.example.exchange_rate.repository.ExchangeRateRepository;
import com.example.exchange_rate.util.exception.CustomException;
import io.reactivex.rxjava3.core.Single;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class ExchangeRateDaoImplTest {

    @InjectMocks
    private ExchangeRateDaoImpl exchangeRateDao;

    @Mock
    private ExchangeRateRepository repository;

    private ExchangeRateEntity exchangeRateEntity;

    @BeforeEach
    void setUp() {
        exchangeRateEntity = buildExchangeRateEntity();
    }

    @Test
    public void whenGetExchangeRateReturnsSuccess() {
        Mockito.when(repository.findByOriginCurrencyAndExchangeCurrency("USD", "PEN"))
                .thenReturn(Optional.of(exchangeRateEntity));

        Single<ExchangeRateEntity> result = exchangeRateDao.getExchangeRate("USD", "PEN");

        result.test().assertValue(exchangeRateEntity);
        Mockito.verify(repository).findByOriginCurrencyAndExchangeCurrency("USD", "PEN");
    }

    @Test
    public void whenGetExchangeRateReturnsEmpty() {

        Mockito.when(repository.findByOriginCurrencyAndExchangeCurrency(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(Optional.empty());

        Single<ExchangeRateEntity> result = exchangeRateDao.getExchangeRate("PEN","USD");

        assertThrows(CustomException.class, () -> {
            exchangeRateDao.getExchangeRate("USD", "PEN").blockingGet();
        });
        Mockito.verify(repository).findByOriginCurrencyAndExchangeCurrency(Mockito.anyString(),Mockito.anyString());
    }


    @Test
    public void whenGetExchangeRateReturnsError() {

        Mockito.when(repository.findByOriginCurrencyAndExchangeCurrency(Mockito.anyString(), Mockito.anyString()))
                .thenThrow(new RuntimeException("Error de JPA"));

        Single<ExchangeRateEntity> result = exchangeRateDao.getExchangeRate("USD", "PEN");
        result.test().assertError(RuntimeException.class);
        Mockito.verify(repository).findByOriginCurrencyAndExchangeCurrency("USD", "PEN");
    }

    private ExchangeRateEntity buildExchangeRateEntity() {
        ExchangeRateEntity e = new ExchangeRateEntity();
        e.setId(1L);
        e.setExchangeAmount(BigDecimal.valueOf(2));
        e.setExchangeCurrency("PEN");
        e.setOriginCurrency("USD");
        return e;
    }
}