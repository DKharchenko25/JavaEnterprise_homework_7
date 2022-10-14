package com.dkharchenko_hillel.homework7.services;

import com.dkharchenko_hillel.homework7.NotFoundException;
import com.dkharchenko_hillel.homework7.models.Product;
import com.dkharchenko_hillel.homework7.reposiroties.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ShopService shopService;

    public ProductServiceImpl(ProductRepository productRepository, ShopService shopService) {
        this.productRepository = productRepository;
        this.shopService = shopService;
    }

    @Override
    public void addProduct(String name, Double price, Long shopId) {
        Product product = new Product(name, price);
        product.setShop(shopService.getShopById(shopId));
        shopService.getShopById(shopId).getProducts().add(product);
        productRepository.save(product);
    }

    @Override
    public void removeProductById(Long id) {
        if (productRepository.existsById(id)) {
            shopService.getShopById(id).getProducts().remove(getProductById(id));
            productRepository.deleteById(id);
        } else {
            try {
                throw new NotFoundException("Product with ID #" + id + " is not found");
            } catch (NotFoundException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    @Override
    public Product getProductById(Long id) {
        if (productRepository.findById(id).isPresent()) {
            return productRepository.findById(id).get();
        } else {
            try {
                throw new NotFoundException("Product with ID #" + id + " is not found");
            } catch (NotFoundException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public void updateProductNameById(Long id, String name) {
        if (productRepository.existsById(id)) {
            productRepository.updateProductNameById(id, name);
        } else {
            try {
                throw new NotFoundException("Product with ID #" + id + " is not found");
            } catch (NotFoundException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    @Override
    public void updateProductPriceById(Long id, Double price) {
        if (productRepository.existsById(id)) {
            productRepository.updateProductSumById(id, price);
        } else {
            try {
                throw new NotFoundException("Product with ID #" + id + " is not found");
            } catch (NotFoundException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }
}
