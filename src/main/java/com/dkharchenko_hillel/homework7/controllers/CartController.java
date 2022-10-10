package com.dkharchenko_hillel.homework7.controllers;

import com.dkharchenko_hillel.homework7.dtos.ProductDto;
import com.dkharchenko_hillel.homework7.services.CartService;
import com.dkharchenko_hillel.homework7.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
        return "redirect:/person_carts";
    }

    @RequestMapping(value = "/remove_cart", method = {RequestMethod.DELETE, RequestMethod.POST, RequestMethod.GET})
    @Transactional
    public String removeCartById(@RequestParam Long id) {
        cartService.removeCartById(id);
        return "redirect:/person_carts";
    }

    @RequestMapping(value = "/get_cart", method = RequestMethod.GET)
    public String getCartById(@RequestParam Long id, Model model) {
        model.addAttribute("cart", cartService.getCartById(id));
        return "getCartSuccess";
    }

    @GetMapping("/all_carts")
    public String getAllCarts(Model model) {
        model.addAttribute("all", cartService.getAllCarts());
        return "allCarts";
    }

    @GetMapping("/person_carts")
    public String getAllPersonCarts(Model model) {
        model.addAttribute("allPersonsCarts",
                cartService.getAllPersonCarts(httpServletRequest.getUserPrincipal().getName()));
        return "allPersonsCarts";
    }

    @RequestMapping(value = "/add_to_cart", method = RequestMethod.GET)
    public String addProductByProductIdView(Model model) {
        model.addAttribute("allCarts",
                cartService.getAllPersonCarts(httpServletRequest.getUserPrincipal().getName()));
        model.addAttribute("allProducts", productService.getAllProducts());
        model.addAttribute("product", new ProductDto());
        return "addProductToCart";
    }

    @RequestMapping(value = "/add_to_cart", method = {RequestMethod.PUT, RequestMethod.POST})
    @Transactional
    public String addProductByProductId(@ModelAttribute("product") ProductDto productDto) {
        cartService.addProductByProductId(productDto.getCartId(), productDto.getId());
        return "redirect:/get_cart?id=" + productDto.getCartId();
    }

    @RequestMapping(value = "/remove_from_cart", method = {RequestMethod.PUT, RequestMethod.POST, RequestMethod.GET})
    @Transactional
    public String removeProductByProductId(@RequestParam Long cartId, @RequestParam Long productId) {
        cartService.removeProductByProductId(cartId, productId);
        return "redirect:/get_cart?id=" + cartId;
    }

    @RequestMapping(value = "/remove_all", method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.POST})
    @Transactional
    public String removeAllProductsById(@RequestParam Long id) {
        cartService.removeAllProductsById(id);
        return "redirect:/person_carts";
    }

}
