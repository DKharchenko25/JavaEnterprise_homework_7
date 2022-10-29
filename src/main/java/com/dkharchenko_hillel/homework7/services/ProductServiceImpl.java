package com.dkharchenko_hillel.homework7.services;

import com.dkharchenko_hillel.homework7.NotFoundException;
import com.dkharchenko_hillel.homework7.models.Product;
import com.dkharchenko_hillel.homework7.reposiroties.ProductRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ShopService shopService;

    public ProductServiceImpl(ProductRepository productRepository, ShopService shopService) {
        this.productRepository = productRepository;
        this.shopService = shopService;
    }

    @Override
    public void addProduct(@NonNull String name, @NonNull Double price, @NonNull Long shopId) {
        checkName(name);
        Product product = new Product(name, price);
        product.setShop(shopService.getShopById(shopId));
        shopService.getShopById(shopId).getProducts().add(product);
        productRepository.save(product);
    }

    @Override
    public void removeProductById(@NonNull Long id) {
        if (productRepository.existsById(id)) {
            shopService.getShopById(id).getProducts()
                    .remove(productRepository.findById(id).orElseThrow(IllegalArgumentException::new));
            productRepository.deleteById(id);
        } else {
            try {
                throw new NotFoundException("Product with ID #" + id + " is not found");
            } catch (NotFoundException e) {
                log.error(e.getMessage());
                throw new IllegalArgumentException(e);
            }
        }
    }

    @Override
    public Product getProductById(@NonNull Long id) {
        if (productRepository.findById(id).isPresent()) {
            return productRepository.findById(id).get();
        } else {
            try {
                throw new NotFoundException("Product with ID #" + id + " is not found");
            } catch (NotFoundException e) {
                log.error(e.getMessage());
                throw new IllegalArgumentException(e);
            }
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public void updateProductNameById(@NonNull Long id, @NonNull String name) {
        checkName(name);
        if (productRepository.existsById(id)) {
            productRepository.updateProductNameById(id, name);
        } else {
            try {
                throw new NotFoundException("Product with ID #" + id + " is not found");
            } catch (NotFoundException e) {
                log.error(e.getMessage());
                throw new IllegalArgumentException(e);
            }
        }
    }

    @Override
    public void updateProductPriceById(@NonNull Long id, @NonNull Double price) {
        if (productRepository.existsById(id)) {
            productRepository.updateProductSumById(id, price);
        } else {
            try {
                throw new NotFoundException("Product with ID #" + id + " is not found");
            } catch (NotFoundException e) {
                log.error(e.getMessage());
                throw new IllegalArgumentException(e);
            }
        }
    }

    private void checkName(String name) {
        if (!name.matches("[A-Za-zА-Яа-я\\d\\-/()]+")) throw new IllegalArgumentException("Invalid name");
    }

}
