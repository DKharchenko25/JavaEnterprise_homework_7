package com.dkharchenko_hillel.homework7.dtos;

import com.dkharchenko_hillel.homework7.models.Shop;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {
    private Long id;
    private Shop shop;
    private String name;
    private Double price;
    private Long shopId;
    private Long cartId;
}
