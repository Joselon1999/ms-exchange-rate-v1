package com.example.exchange_rate.mapper;

import com.example.exchange_rate.dto.ExchangeRateResponse;
import com.example.exchange_rate.dto.entity.ExchangeRate;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface MapperConfiguration {

    @Mapping(target = "originalCurrency", source = "exchangeRate.monedaOrigen")
    @Mapping(target = "exchangeCurrency", source = "exchangeRate.monedaDestino")
    @Mapping(target = "originalAmount", source = "originalAmount")
    @Mapping(target = "exchangeAmount", expression = "java(originalAmount * exchangeRate.getMontoTipoCambio())")
    ExchangeRateResponse mapExchangeRateResponse(ExchangeRate exchangeRate, double originalAmount);

}
