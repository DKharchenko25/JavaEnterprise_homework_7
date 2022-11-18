package com.dkharchenko_hillel.homework7.clients;

import com.dkharchenko_hillel.homework7.models.Currency;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CurrencyClientImpl implements CurrencyClient {
    private static final String PRIVAT_BANK_CURRENCY = "https://api.privatbank.ua/p24api/pubinfo?exchange&coursid=5";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final RestTemplate restTemplate;

    public CurrencyClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Currency getCurrencyInUsd() {
        ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(PRIVAT_BANK_CURRENCY, Object[].class);
        Object[] objects = responseEntity.getBody();
        return Arrays.stream(Objects.requireNonNull(objects)).map(object -> MAPPER.convertValue(object, Currency.class))
                .collect(Collectors.toList())
                .stream()
                .filter(currency -> currency.getCcy().equalsIgnoreCase("usd"))
                .findAny().orElseThrow(IllegalArgumentException::new);
    }
}
