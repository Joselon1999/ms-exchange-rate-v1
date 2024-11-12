package com.example.exchange_rate.mapper;

import com.example.exchange_rate.dto.ExchangeRateRequest;
import com.example.exchange_rate.dto.ExchangeRateResponse;
import com.example.exchange_rate.dto.domain.ExchangeRate;
import com.example.exchange_rate.dto.entity.ExchangeRateEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface MapperConfiguration {

    ExchangeRate fromExchangeRateRequestToExchangeRate(ExchangeRateRequest exchangeRate);

    @Mapping(target = "originalCurrency", source = "e.originCurrency")
    @Mapping(target = "exchangeCurrency", source = "e.exchangeCurrency")
    @Mapping(target = "originalAmount", source = "originalAmount")
    @Mapping(target = "exchangeAmount", expression = "java(e.getExchangeAmount().multiply(originalAmount))")
    @Mapping(target = "exchangeRate", source = "e.exchangeAmount")
    ExchangeRate fromExchangeRateEntityToExchangeRate(ExchangeRateEntity e, BigDecimal originalAmount);

    ExchangeRateResponse fromExchangeRateToExchangeRateResponse(ExchangeRate exchangeRate);

    @Mapping(target = "originalCurrency", source = "e.originalCurrency")
    @Mapping(target = "exchangeCurrency", source = "e.exchangeCurrency")
    @Mapping(target = "originalAmount", source = "originalAmount")
    @Mapping(target = "exchangeAmount", expression = "java(e.getExchangeRate().multiply(originalAmount))")
    @Mapping(target = "exchangeRate", source = "e.exchangeRate")
    ExchangeRate fromCacheToExchangeRateResponse(ExchangeRate e, BigDecimal originalAmount);

}
