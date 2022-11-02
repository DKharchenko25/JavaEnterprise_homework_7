package com.dkharchenko_hillel.homework7.controllers;

import com.dkharchenko_hillel.homework7.dtos.ProductDto;
import com.dkharchenko_hillel.homework7.facades.CartFacade;
import com.dkharchenko_hillel.homework7.facades.ProductFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class CartController {

    private final CartFacade cartFacade;
    private final ProductFacade productFacade;

    private final HttpServletRequest httpServletRequest;

    public CartController(CartFacade cartFacade, ProductFacade productFacade, HttpServletRequest httpServletRequest) {
        this.cartFacade = cartFacade;
        this.productFacade = productFacade;
        this.httpServletRequest = httpServletRequest;
    }

    @RequestMapping(value = "/add_cart", method = {RequestMethod.POST, RequestMethod.GET})
    public String addCart() {
        cartFacade.addCartByPersonUsername(httpServletRequest.getUserPrincipal().getName());
        log.info("New cart is added for user: {}", httpServletRequest.getUserPrincipal().getName());
        return "redirect:/person_carts";
    }

    @RequestMapping(value = "/remove_cart", method = {RequestMethod.DELETE, RequestMethod.POST, RequestMethod.GET})
    @Transactional
    public String removeCartById(@RequestParam Long id) {
        cartFacade.removeCartById(id);
        log.info("Cart is removed: {}", id);
        return "redirect:/person_carts";
    }

    @RequestMapping(value = "/get_cart", method = RequestMethod.GET)
    public String getCartById(@RequestParam Long id, Model model) {
        model.addAttribute("cart", cartFacade.getCartById(id));
        return "getCartSuccess";
    }

    @GetMapping("/all_carts")
    public String getAllCarts(Model model) {
        model.addAttribute("all", cartFacade.getAllCarts());
        return "allCarts";
    }

    @GetMapping("/person_carts")
    public String getAllPersonCarts(Model model) {
        model.addAttribute("allPersonsCarts",
                cartFacade.getAllPersonCarts(httpServletRequest.getUserPrincipal().getName()));
        return "allPersonsCarts";
    }

    @RequestMapping(value = "/add_to_cart", method = RequestMethod.GET)
    public String addProductByProductIdView(Model model) {
        model.addAttribute("allCarts",
                cartFacade.getAllPersonCarts(httpServletRequest.getUserPrincipal().getName()));
        model.addAttribute("allProducts", productFacade.getAllProducts());
        model.addAttribute("product", new ProductDto());
        return "addProductToCart";
    }

    @RequestMapping(value = "/add_to_cart", method = {RequestMethod.PUT, RequestMethod.POST})
    @Transactional
    public String addProductByProductId(@ModelAttribute("product") ProductDto productDto) {
        cartFacade.addProductById(productDto);
        log.info("Product #{}, is added to cart #{} ", productDto.getId(), productDto.getCartId());
        return "redirect:/get_cart?id=" + productDto.getCartId();
    }

    @RequestMapping(value = "/remove_from_cart", method = {RequestMethod.PUT, RequestMethod.POST, RequestMethod.GET})
    @Transactional
    public String removeProductByProductId(@RequestParam Long cartId, @RequestParam Long productId) {
        cartFacade.removeProductById(cartId, productId);
        log.info("Product #{}, is removed from cart #{} ", productId, cartId);
        return "redirect:/get_cart?id=" + cartId;
    }

    @RequestMapping(value = "/remove_all", method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST})
    @Transactional
    public String removeAllProductsById(@RequestParam Long id) {
        cartFacade.removeAllProductsById(id);
        log.info("All products are removed from cart #{} ", id);
        return "redirect:/person_carts";
    }
}
