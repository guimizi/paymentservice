package com.guimizi.challenge.paymentservice.service.common;

import com.guimizi.challenge.paymentservice.model.api.exception.ApiException;
import com.guimizi.challenge.paymentservice.model.api.exception.ErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyConversionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyConversionService.class);

    private Map<String, BigDecimal> rateToArsByCurrency;

    public CurrencyConversionService() {
        this.init();
    }

    private void init() {
        this.rateToArsByCurrency = new HashMap<>();
        this.rateToArsByCurrency.put("USD", new BigDecimal("59.57"));
        this.rateToArsByCurrency.put("ARS", BigDecimal.ONE);
    }

    public BigDecimal convert(BigDecimal amount, String currency) {
        LOGGER.debug("Normalizing amount: {} - currency: {}", amount, currency );
        BigDecimal toArsRate = this.rateToArsByCurrency.get(currency.toUpperCase());
        if(toArsRate == null) {
            throw new ApiException(ErrorType.NOT_SUPPORTED_CURRENCY_EXCEPTION);
        }
        BigDecimal normalizedAmount = amount.multiply(toArsRate)
                .setScale(2, RoundingMode.HALF_UP);
        LOGGER.debug("Amount normalized to: {}", normalizedAmount);
        return normalizedAmount;
    }

}
