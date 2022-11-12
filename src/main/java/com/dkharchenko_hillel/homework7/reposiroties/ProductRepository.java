package com.dkharchenko_hillel.homework7.reposiroties;

import com.dkharchenko_hillel.homework7.models.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    @Modifying
    @Query("update Product set name = ?2 where id = ?1")
    Integer updateProductNameById(Long id, String name);

    @Modifying
    @Query("update Product set price = ?2 where id = ?1")
    Integer updateProductPriceById(Long id, BigDecimal price);
}
