package com.example.exchange_rate.controller;

import com.example.exchange_rate.dto.ExchangeRateRequest;
import com.example.exchange_rate.dto.ExchangeRateResponse;
import com.example.exchange_rate.mapper.MapperConfiguration;
import com.example.exchange_rate.service.ExchangeRateService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.reactivex.rxjava3.core.Single;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/exchange-rate")
@Slf4j
public class ExchangeRateController {

    @Autowired
    private ExchangeRateService exchangeRateService;

    @Autowired
    private MapperConfiguration mapperConfiguration;

    @PostMapping("/")
    public Single<ExchangeRateResponse> getExchangeRate(@Valid @RequestBody ExchangeRateRequest request) {

        return Single.just(mapperConfiguration.fromExchangeRateRequestToExchangeRate(request))
                        .flatMap(exchangeRateService::getExchangeRate)
                .map(mapperConfiguration::fromExchangeRateToExchangeRateResponse)
                .doOnSuccess(r -> log.info("Success on ExchangeRateController.getExchangeRate"))
                .doOnError(th -> log.error("Error on ExchangeRateController.getExchangeRate"));
    }
}
