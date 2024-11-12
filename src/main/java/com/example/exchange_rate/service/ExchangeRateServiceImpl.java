package com.example.exchange_rate.service;

import com.example.exchange_rate.dao.ExchangeRateDao;
import com.example.exchange_rate.dao.SessionDao;
import com.example.exchange_rate.dto.domain.ExchangeRate;
import com.example.exchange_rate.mapper.MapperConfiguration;
import io.reactivex.rxjava3.core.Single;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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
    public Single<ExchangeRate> getExchangeRate(ExchangeRate request,String coupon) {
        return sessionDao.findById(request)
                .switchIfEmpty(exchangeRateDao.getExchangeRate(request.getOriginalCurrency(),request.getExchangeCurrency())
                        .map(exchangeRate -> mapperConfiguration.fromExchangeRateEntityToExchangeRate(exchangeRate,request.getOriginalAmount()))
                        .map(response -> {
                            sessionDao.save(response).subscribe();
                            return response;
                        }))
                .map(exchangeRate -> {//todo validar nulo
                    if (coupon.equalsIgnoreCase("123")) {//comparo con properties
                        log.info("ERate: {}",exchangeRate.getExchangeRate());
                        exchangeRate.setExchangeRate(exchangeRate.getExchangeRate().add(BigDecimal.valueOf(0.05)));
                        log.info("Se agrega cupon");
                        log.info("ERate: {}",exchangeRate.getExchangeRate());
                    }
                    return exchangeRate;
                })
                .map(exRate -> mapperConfiguration.fromCacheToExchangeRateResponse(exRate,request.getOriginalAmount()))
                .doOnSuccess(r -> log.info("Success on ExchangeRateServiceImpl.getExchangeRate"))
                .doOnError(th -> log.error("Error on ExchangeRateServiceImpl.getExchangeRate",th));
    }
}
