package com.dkharchenko_hillel.homework7.converters;

import com.dkharchenko_hillel.homework7.clients.CurrencyClient;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class PriceConverter {

    private final CurrencyClient currencyClient;

    public PriceConverter(CurrencyClient currencyClient) {
        this.currencyClient = currencyClient;
    }

    public BigDecimal getConvertedPrice(BigDecimal price) {
        BigDecimal currencyInUsd = currencyClient.getCurrencyInUsd().getSale();
        return price.divide(currencyInUsd, RoundingMode.UP);
    }
}
