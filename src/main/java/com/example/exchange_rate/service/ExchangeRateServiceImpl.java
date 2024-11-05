package com.example.exchange_rate.service;

import com.example.exchange_rate.dao.ExchangeRateDao;
import com.example.exchange_rate.dao.SessionDao;
import com.example.exchange_rate.dto.ExchangeRateRequest;
import com.example.exchange_rate.dto.ExchangeRateResponse;
import com.example.exchange_rate.mapper.MapperConfiguration;
import io.reactivex.rxjava3.core.Single;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ExchangeRateServiceImpl implements ExchangeRateService{

    @Autowired
    private ExchangeRateDao exchangeRateDao;

    @Autowired
    private SessionDao sessionDao;
    @Autowired
    private MapperConfiguration mapperConfiguration;

    @Override
    public Single<ExchangeRateResponse> getExchangeRate(ExchangeRateRequest request) {
        return sessionDao.findById(request)
                .switchIfEmpty(exchangeRateDao.getExchangeRate(request.getOriginalCurrency(),request.getExchangeCurrency())
                        .map(exchangeRate -> mapperConfiguration.mapExchangeRateResponse(
                                exchangeRate,request.getOriginalAmount()))
                        .flatMap(response -> sessionDao.save(response)))
                .doOnSuccess(r -> log.info("Success on ExchangeRateServiceImpl.getExchangeRate"))
                .doOnError(th -> log.error("Error on ExchangeRateServiceImpl.getExchangeRate",th));
    }
}
