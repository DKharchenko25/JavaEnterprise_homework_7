package com.dkharchenko_hillel.homework7.reposiroties;

import com.dkharchenko_hillel.homework7.models.Cart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {

}
