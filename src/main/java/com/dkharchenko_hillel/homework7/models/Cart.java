package com.dkharchenko_hillel.homework7.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Person person;
    @OneToMany(orphanRemoval = true)
    @JoinColumn
    private List<Product> products = new ArrayList<>();
    private Integer amountOfProducts = 0;
    private BigDecimal sum = new BigDecimal("0.00");

    public Cart(Person person) {
        this.person = person;
    }
}
