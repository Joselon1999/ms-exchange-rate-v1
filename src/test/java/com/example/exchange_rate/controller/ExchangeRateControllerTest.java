package com.example.exchange_rate.controller;

import com.example.exchange_rate.dto.ExchangeRateRequest;
import com.example.exchange_rate.dto.ExchangeRateResponse;
import com.example.exchange_rate.dto.domain.ExchangeRate;
import com.example.exchange_rate.mapper.MapperConfiguration;
import com.example.exchange_rate.service.ExchangeRateService;
import io.reactivex.rxjava3.core.Single;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ExchangeRateControllerTest {


    @InjectMocks
    private ExchangeRateController exchangeRateController;

    @Mock
    private ExchangeRateService exchangeRateService;

    @Mock
    private MapperConfiguration mapperConfiguration;

    private ExchangeRateRequest exchangeRateRequest;
    private ExchangeRateResponse exchangeRateResponse;

    @BeforeEach
    public void setUp() {
        exchangeRateRequest = buildExchangeRateRequest();
        exchangeRateResponse = buildExchangeRateResponse();
    }

    @Test
    public void whenGetExchangeRateReturnsSuccess() {

        Mockito.when(mapperConfiguration.fromExchangeRateRequestToExchangeRate(exchangeRateRequest))
                .thenReturn(buildExchangeRate());
        Mockito.when(exchangeRateService.getExchangeRate(Mockito.any()))
                .thenReturn(Single.just(buildExchangeRate()));
        Mockito.when(mapperConfiguration.fromExchangeRateToExchangeRateResponse(Mockito.any()))
                .thenReturn(exchangeRateResponse);

        Single<ExchangeRateResponse> result = exchangeRateController.getExchangeRate(exchangeRateRequest);

        assertNotNull(result);
        result.test().assertValue(exchangeRateResponse);
        Mockito.verify(mapperConfiguration).fromExchangeRateRequestToExchangeRate(exchangeRateRequest);
        Mockito.verify(exchangeRateService).getExchangeRate(Mockito.any());
        Mockito.verify(mapperConfiguration).fromExchangeRateToExchangeRateResponse(Mockito.any());
    }

    @Test
    public void whenGetExchangeRateReturnsError() {

        Mockito.when(mapperConfiguration.fromExchangeRateRequestToExchangeRate(exchangeRateRequest))
                .thenReturn(buildExchangeRate());
        Mockito.when(exchangeRateService.getExchangeRate(Mockito.any()))
                .thenReturn(Single.error(new RuntimeException("Error fetching exchange rate")));

        Single<ExchangeRateResponse> result = exchangeRateController.getExchangeRate(exchangeRateRequest);

        assertNotNull(result);
        result.test().assertError(RuntimeException.class);
        Mockito.verify(mapperConfiguration).fromExchangeRateRequestToExchangeRate(exchangeRateRequest);
        Mockito.verify(exchangeRateService).getExchangeRate(Mockito.any());
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

    private ExchangeRateRequest buildExchangeRateRequest() {
        return ExchangeRateRequest.builder()
                .originalAmount(BigDecimal.valueOf(1))
                .exchangeCurrency("PEN")
                .originalCurrency("USD")
                .build();
    }

    private ExchangeRateResponse buildExchangeRateResponse() {
        return ExchangeRateResponse.builder()
                .exchangeAmount(BigDecimal.valueOf(2))
                .originalAmount(BigDecimal.valueOf(1))
                .exchangeRate(BigDecimal.valueOf(2))
                .exchangeCurrency("PEN")
                .originalCurrency("USD")
                .build();
    }
}