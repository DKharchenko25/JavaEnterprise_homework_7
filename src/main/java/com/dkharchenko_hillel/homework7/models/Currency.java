package com.dkharchenko_hillel.homework7.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Currency {
    private String ccy;
    @JsonProperty("base_ccy")
    private String baseCcy;
    private BigDecimal buy;
    private BigDecimal sale;
}
