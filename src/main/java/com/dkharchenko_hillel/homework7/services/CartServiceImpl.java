package com.dkharchenko_hillel.homework7.services;

import com.dkharchenko_hillel.homework7.NotFoundException;
import com.dkharchenko_hillel.homework7.models.Cart;
import com.dkharchenko_hillel.homework7.models.Person;
import com.dkharchenko_hillel.homework7.models.Product;
import com.dkharchenko_hillel.homework7.reposiroties.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final PersonService personService;
    private final ProductService productService;

    public CartServiceImpl(CartRepository cartRepository, PersonService personService, ProductService productService) {
        this.cartRepository = cartRepository;
        this.personService = personService;
        this.productService = productService;
    }

    @Override
    public void addCartByPersonUsername(String username) {
        Cart cart = new Cart(personService.getPersonByUsername(username));
        personService.getPersonByUsername(username).getCarts().add(cart);
        cartRepository.save(cart);
    }

    @Override
    public void removeCartById(Long id) {
        if (cartRepository.findById(id).isPresent()) {
            Cart cart = cartRepository.findById(id).get();
            personService.getPersonById(cart.getPerson().getId()).getCarts().remove(cart);
            cartRepository.deleteById(id);
        } else {
            try {
                throw new NotFoundException("Cart with ID #" + id + " is not found");
            } catch (NotFoundException e) {
                log.error(e.getMessage());
                throw new IllegalArgumentException(e);
            }
        }
    }

    @Override
    public Cart getCartById(Long id) {
        if (cartRepository.findById(id).isPresent()) {
            return cartRepository.findById(id).get();
        } else {
            try {
                throw new NotFoundException("Cart with ID #" + id + " is not found");
            } catch (NotFoundException e) {
                log.error(e.getMessage());
                throw new IllegalArgumentException(e);
            }
        }
    }

    @Override
    public List<Cart> getAllCarts() {
        return (List<Cart>) cartRepository.findAll();
    }

    @Override
    public List<Cart> getAllPersonCarts(String username) {
        Person person = personService.getPersonByUsername(username);
        return getAllCarts().stream().filter(cart -> cart.getPerson().getUsername().equals(person.getUsername()))
                .collect(Collectors.toList());
    }

    @Override
    public void addProductByProductId(Long cartId, Long productId) {
        if (cartRepository.findById(cartId).isPresent()) {
            Cart cart = cartRepository.findById(cartId).get();
            Product product = productService.getProductById(productId);
            checkContainsProduct(cart, product);
            cart.getProducts().add(product);
            increaseAmountAndSum(cart, product);
        } else {
            try {
                throw new NotFoundException("Cart with ID #" + cartId + " is not found");
            } catch (NotFoundException e) {
                log.error(e.getMessage());
                throw new IllegalArgumentException(e);
            }
        }
    }

    @Override
    public void removeProductByProductId(Long cartId, Long productId) {
        if (cartRepository.findById(cartId).isPresent()) {
            Cart cart = cartRepository.findById(cartId).get();
            Product product = productService.getProductById(productId);
            checkNotContainsProduct(cart, product);
            decreaseAmountAndSum(cart, product);
            cart.getProducts().remove(product);
        } else {
            try {
                throw new NotFoundException("Cart with ID #" + cartId + " is not found");
            } catch (NotFoundException e) {
                log.error(e.getMessage());
                throw new IllegalArgumentException(e);
            }
        }
    }

    @Override
    public void removeAllProductsById(Long id) {
        if (cartRepository.findById(id).isPresent()) {
            Cart cart = cartRepository.findById(id).get();
            cart.getProducts().clear();
            cart.setSum(new BigDecimal("0.00"));
            cart.setAmountOfProducts(0);
        } else {
            try {
                throw new NotFoundException("Cart with ID #" + id + " is not found");
            } catch (NotFoundException e) {
                log.error(e.getMessage());
                throw new IllegalArgumentException(e);
            }
        }
    }

    private void checkNotContainsProduct(Cart cart, Product product) {
        if (!cart.getProducts().contains(product)) {
            try {
                throw new NotFoundException("Cart don't contains product with ID #" + product.getId());
            } catch (NotFoundException e) {
                log.error(e.getMessage());
                throw new IllegalArgumentException(e);
            }
        }
    }

    private void checkContainsProduct(Cart cart, Product product) {
        if (cart.getProducts().contains(product)) {
            try {
                throw new NotFoundException("Cart is already contains product with ID #" + product.getId());
            } catch (NotFoundException e) {
                log.error(e.getMessage());
                throw new IllegalArgumentException(e);
            }
        }
    }

    private void increaseAmountAndSum(Cart cart, Product product) {
        cart.setAmountOfProducts(cart.getAmountOfProducts() + 1);
        cart.setSum(cart.getSum().add(BigDecimal.valueOf(product.getPrice())));
    }

    private void decreaseAmountAndSum(Cart cart, Product product) {
        if (cart.getSum().compareTo(new BigDecimal("0.00")) != 0
                && cart.getAmountOfProducts().compareTo(0) != 0) {
            cart.setAmountOfProducts(cart.getAmountOfProducts() - 1);
            cart.setSum(cart.getSum().subtract(BigDecimal.valueOf(product.getPrice())));
        } else {
            cart.setSum(new BigDecimal("0.00"));
            cart.setAmountOfProducts(0);
        }
    }
}
