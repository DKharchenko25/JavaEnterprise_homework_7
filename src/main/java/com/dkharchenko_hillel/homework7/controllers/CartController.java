package com.dkharchenko_hillel.homework7.controllers;

import com.dkharchenko_hillel.homework7.converters.CartConverter;
import com.dkharchenko_hillel.homework7.converters.ProductConverter;
import com.dkharchenko_hillel.homework7.dtos.ProductDto;
import com.dkharchenko_hillel.homework7.services.CartService;
import com.dkharchenko_hillel.homework7.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

import static com.dkharchenko_hillel.homework7.converters.CartConverter.convertCartToCartDto;

@Slf4j
@Controller
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    private final HttpServletRequest httpServletRequest;

    public CartController(CartService cartService, ProductService productService, HttpServletRequest httpServletRequest) {
        this.cartService = cartService;
        this.productService = productService;
        this.httpServletRequest = httpServletRequest;
    }

    @RequestMapping(value = "/add_cart", method = {RequestMethod.POST, RequestMethod.GET})
    public String addCart() {
        cartService.addCartByPersonUsername(httpServletRequest.getUserPrincipal().getName());
        log.info("New cart is added for user: {}", httpServletRequest.getUserPrincipal().getName());
        return "redirect:/person_carts";
    }

    @RequestMapping(value = "/remove_cart", method = {RequestMethod.DELETE, RequestMethod.POST, RequestMethod.GET})
    @Transactional
    public String removeCartById(@RequestParam Long id) {
        cartService.removeCartById(id);
        log.info("Cart is removed: {}", id);
        return "redirect:/person_carts";
    }

    @RequestMapping(value = "/get_cart", method = RequestMethod.GET)
    public String getCartById(@RequestParam Long id, Model model) {
        model.addAttribute("cart", convertCartToCartDto(cartService.getCartById(id)));
        return "getCartSuccess";
    }

    @GetMapping("/all_carts")
    public String getAllCarts(Model model) {
        model.addAttribute("all", cartService.getAllCarts().stream()
                .map(CartConverter::convertCartToCartDto).collect(Collectors.toList()));
        return "allCarts";
    }

    @GetMapping("/person_carts")
    public String getAllPersonCarts(Model model) {
        model.addAttribute("allPersonsCarts",
                cartService.getAllPersonCarts(httpServletRequest.getUserPrincipal().getName())
                        .stream().map(CartConverter::convertCartToCartDto).collect(Collectors.toList()));
        return "allPersonsCarts";
    }

    @RequestMapping(value = "/add_to_cart", method = RequestMethod.GET)
    public String addProductByProductIdView(Model model) {
        model.addAttribute("allCarts",
                cartService.getAllPersonCarts(httpServletRequest.getUserPrincipal().getName())
                        .stream().map(CartConverter::convertCartToCartDto).collect(Collectors.toList()));
        model.addAttribute("allProducts", productService.getAllProducts().stream()
                .map(ProductConverter::convertProductToProductDto).collect(Collectors.toList()));
        model.addAttribute("product", new ProductDto());
        return "addProductToCart";
    }

    @RequestMapping(value = "/add_to_cart", method = {RequestMethod.PUT, RequestMethod.POST})
    @Transactional
    public String addProductByProductId(@ModelAttribute("product") ProductDto productDto) {
        cartService.addProductByProductId(productDto.getCartId(), productDto.getId());
        log.info("Product #{}, is added to cart #{} ", productDto.getId(), productDto.getCartId());
        return "redirect:/get_cart?id=" + productDto.getCartId();
    }

    @RequestMapping(value = "/remove_from_cart", method = {RequestMethod.PUT, RequestMethod.POST, RequestMethod.GET})
    @Transactional
    public String removeProductByProductId(@RequestParam Long cartId, @RequestParam Long productId) {
        cartService.removeProductByProductId(cartId, productId);
        log.info("Product #{}, is removed from cart #{} ", productId, cartId);
        return "redirect:/get_cart?id=" + cartId;
    }

    @RequestMapping(value = "/remove_all", method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST})
    @Transactional
    public String removeAllProductsById(@RequestParam Long id) {
        cartService.removeAllProductsById(id);
        log.info("All products are removed from cart #{} ", id);
        return "redirect:/person_carts";
    }

}
