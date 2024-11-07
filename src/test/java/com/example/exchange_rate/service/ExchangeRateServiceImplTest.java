package com.example.exchange_rate.service;

import com.example.exchange_rate.dao.ExchangeRateDao;
import com.example.exchange_rate.dao.SessionDao;
import com.example.exchange_rate.dto.domain.ExchangeRate;
import com.example.exchange_rate.dto.entity.ExchangeRateEntity;
import com.example.exchange_rate.mapper.MapperConfiguration;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class ExchangeRateServiceImplTest {

    @Mock
    private ExchangeRateDao exchangeRateDao;

    @Mock
    private SessionDao sessionDao;

    @Mock
    private MapperConfiguration mapperConfiguration;

    @InjectMocks
    private ExchangeRateServiceImpl exchangeRateService;

    private ExchangeRate request;
    private ExchangeRateEntity exchangeRateEntity;
    private ExchangeRate expectedResponse;

    @BeforeEach
    public void setup() {
        request = buildExchangeRate();

        exchangeRateEntity = new ExchangeRateEntity();
        expectedResponse = new ExchangeRate();
        expectedResponse.setExchangeRate(BigDecimal.valueOf(2));
    }

    @Test
    public void whenGetExchangeRateReturnFromSessionDao() {

        Mockito.when(sessionDao.findById(request)).thenReturn(Maybe.just(expectedResponse));
        Mockito.when(exchangeRateDao.getExchangeRate(request.getOriginalCurrency(), request.getExchangeCurrency()))
                .thenReturn(Single.just(exchangeRateEntity));

        Single<ExchangeRate> result = exchangeRateService.getExchangeRate(request);

        result.test().assertValue(response -> response.equals(expectedResponse))
                .assertComplete();

        Mockito.verify(sessionDao, Mockito.never()).save(Mockito.any());
    }

    @Test
    public void whenGetExchangeRateReturnFromExchangeRateDaoAndSaveToSessionDao() {

        Mockito.when(sessionDao.findById(request)).thenReturn(Maybe.empty());
        Mockito.when(exchangeRateDao.getExchangeRate(request.getOriginalCurrency(), request.getExchangeCurrency()))
                .thenReturn(Single.just(exchangeRateEntity));
        Mockito.when(mapperConfiguration.fromExchangeRateEntityToExchangeRate(exchangeRateEntity, request.getOriginalAmount()))
                .thenReturn(expectedResponse);
        Mockito.when(sessionDao.save(expectedResponse)).thenReturn(Completable.complete());

        exchangeRateService.getExchangeRate(request)
                .test()
                .assertValue(response -> response.equals(expectedResponse))
                .assertComplete();

        Mockito.verify(exchangeRateDao).getExchangeRate(request.getOriginalCurrency(), request.getExchangeCurrency());
        Mockito.verify(sessionDao).save(expectedResponse);
    }

    @Test
    public void whenGetExchangeRateReturnErrorHandling() {
        Mockito.when(sessionDao.findById(request)).thenReturn(Maybe.empty());
        Mockito.when(exchangeRateDao.getExchangeRate(request.getOriginalCurrency(), request.getExchangeCurrency()))
                .thenReturn(Single.error(new RuntimeException("Data not found")));

        exchangeRateService.getExchangeRate(request)
                .test()
                .assertError(RuntimeException.class);

        Mockito.verify(sessionDao, Mockito.never()).save(Mockito.any());
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