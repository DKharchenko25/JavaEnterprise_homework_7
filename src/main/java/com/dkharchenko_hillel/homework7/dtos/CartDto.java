package com.dkharchenko_hillel.homework7.dtos;

import com.dkharchenko_hillel.homework7.models.Person;
import com.dkharchenko_hillel.homework7.models.Product;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CartDto {
    private Long id;
    private Person person;
    private List<Product> products;
    private Integer amountOfProducts;
    private BigDecimal sum;
}
