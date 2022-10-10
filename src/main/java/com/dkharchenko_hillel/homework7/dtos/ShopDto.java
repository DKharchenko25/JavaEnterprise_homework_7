package com.dkharchenko_hillel.homework7.dtos;

import com.dkharchenko_hillel.homework7.models.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShopDto {
    private Long id;
    private String name;
    private List<Product> products;

}
