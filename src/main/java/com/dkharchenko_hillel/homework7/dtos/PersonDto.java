package com.dkharchenko_hillel.homework7.dtos;

import com.dkharchenko_hillel.homework7.models.Cart;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PersonDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String username;
    private String password;
    private String passwordConfirm;
    private List<Cart> carts;
}
