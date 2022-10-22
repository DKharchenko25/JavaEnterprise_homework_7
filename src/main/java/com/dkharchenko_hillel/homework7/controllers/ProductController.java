package com.dkharchenko_hillel.homework7.controllers;

import com.dkharchenko_hillel.homework7.converters.ProductConverter;
import com.dkharchenko_hillel.homework7.dtos.ProductDto;
import com.dkharchenko_hillel.homework7.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.stream.Collectors;

@Slf4j
@Controller
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @RequestMapping(value = "/add_product", method = RequestMethod.GET)
    public String addProductView(Model model) {
        model.addAttribute("product", new ProductDto());
        return "addProduct";
    }

    @RequestMapping(value = "/add_product", method = RequestMethod.POST)
    public String addProduct(@ModelAttribute("product") ProductDto productDto) {
        productService.addProduct(productDto.getName(), productDto.getPrice(), productDto.getShopId());
        log.info("New product is added to products table: {}, {}", productDto.getName(), productDto.getPrice());
        return "addProductSuccess";
    }

    @RequestMapping(value = "/remove_product", method = RequestMethod.GET)
    public String removeProductByIdView(Model model) {
        model.addAttribute("product", new ProductDto());
        return "removeProduct";
    }

    @RequestMapping(value = "/remove_product", method = {RequestMethod.DELETE, RequestMethod.POST})
    @Transactional
    public String removeProductById(@ModelAttribute("person") ProductDto productDto) {
        productService.removeProductById(productDto.getId());
        log.info("Product is removed from products table: {}", productDto.getId());
        return "removeProductSuccess";
    }

    @GetMapping("/all_products")
    public String getAllProducts(Model model) {
        model.addAttribute("all", productService.getAllProducts()
                .stream().map(ProductConverter::convertProductToProductDto).collect(Collectors.toList()));
        return "allProducts";
    }

    @RequestMapping(value = "/update_product_name", method = RequestMethod.GET)
    public String updateProductNameByIdView(Model model) {
        model.addAttribute("product", new ProductDto());
        return "updateProductName";
    }

    @RequestMapping(value = "/update_product_name", method = {RequestMethod.PUT, RequestMethod.POST})
    @Transactional
    public String updateProductNameById(@ModelAttribute("product") ProductDto productDto) {
        productService.updateProductNameById(productDto.getId(), productDto.getName());
        log.info("Product is updated: {}", productDto.getId());
        return "updateProductNameSuccess";
    }

    @RequestMapping(value = "/update_price", method = RequestMethod.GET)
    public String updateProductPriceByIdView(Model model) {
        model.addAttribute("product", new ProductDto());
        return "updateProductPrice";
    }

    @RequestMapping(value = "/update_price", method = {RequestMethod.PUT, RequestMethod.POST})
    @Transactional
    public String updateProductPriceById(@ModelAttribute("product") ProductDto productDto) {
        productService.updateProductPriceById(productDto.getId(), productDto.getPrice());
        log.info("Product is updated: {}", productDto.getId());
        return "updateProductPriceSuccess";
    }
}
