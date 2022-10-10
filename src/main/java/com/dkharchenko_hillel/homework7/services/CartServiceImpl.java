package com.dkharchenko_hillel.homework7.services;

import com.dkharchenko_hillel.homework7.NotFoundException;
import com.dkharchenko_hillel.homework7.dtos.CartDto;
import com.dkharchenko_hillel.homework7.models.Cart;
import com.dkharchenko_hillel.homework7.models.Person;
import com.dkharchenko_hillel.homework7.models.Product;
import com.dkharchenko_hillel.homework7.reposiroties.CartRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.dkharchenko_hillel.homework7.converters.CartConverter.convertCartToCartDto;
import static com.dkharchenko_hillel.homework7.converters.ProductConverter.convertProductDtoToProduct;

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
    public Long addCartByPersonUsername(String username) {
        Cart cart = new Cart(personService.getPersonByUsername(username));
        personService.getPersonByUsername(username).getCarts().add(cart);
        return cartRepository.save(cart).getId();
    }

    @Override
    public Long removeCartById(Long id) {
        if (cartRepository.findById(id).isPresent()) {
            Cart cart = cartRepository.findById(id).get();
            personService.getPersonById(cart.getPerson().getId()).getCarts().remove(cart);
            cartRepository.deleteById(id);
            return id;
        }
        try {
            throw new NotFoundException("Cart with ID #" + id + " is not found");
        } catch (NotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public CartDto getCartById(Long id) {
        if (cartRepository.findById(id).isPresent()) {
            return convertCartToCartDto(cartRepository.findById(id).get());
        }
        try {
            throw new NotFoundException("Cart with ID #" + id + " is not found");
        } catch (NotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public List<CartDto> getAllCarts() {
        List<CartDto> cartDtoList = new ArrayList<>();
        cartRepository.findAll().forEach(cart -> cartDtoList.add(convertCartToCartDto(cart)));
        return cartDtoList;
    }

    @Override
    public List<CartDto> getAllPersonCarts(String username) {
        Person person = personService.getPersonByUsername(username);
        return getAllCarts().stream().filter(cart -> cart.getPerson().getUsername().equals(person.getUsername()))
                .collect(Collectors.toList());
    }

    @Override
    public CartDto addProductByProductId(Long cartId, Long productId) {
        if (cartRepository.findById(cartId).isPresent()) {
            Cart cart = cartRepository.findById(cartId).get();
            Product product = convertProductDtoToProduct(productService.getProductById(productId));
            checkContainsProduct(cart, product);
            cart.getProducts().add(product);
            increaseAmountAndSum(cart, product);
            return convertCartToCartDto(cart);
        }
        try {
            throw new NotFoundException("Cart with ID #" + cartId + " is not found");
        } catch (NotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public CartDto removeProductByProductId(Long cartId, Long productId) {
        if (cartRepository.findById(cartId).isPresent()) {
            Cart cart = cartRepository.findById(cartId).get();
            Product product = convertProductDtoToProduct(productService.getProductById(productId));
            checkNotContainsProduct(cart, product);
            decreaseAmountAndSum(cart, product);
            cart.getProducts().remove(product);
            return convertCartToCartDto(cart);
        }
        try {
            throw new NotFoundException("Cart with ID #" + cartId + " is not found");
        } catch (NotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Long removeAllProductsById(Long id) {
        if (cartRepository.findById(id).isPresent()) {
            Cart cart = cartRepository.findById(id).get();
            cart.getProducts().clear();
            cart.setSum(new BigDecimal("0.00"));
            cart.setAmountOfProducts(0);
            return id;
        }
        try {
            throw new NotFoundException("Cart with ID #" + id + " is not found");
        } catch (NotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void checkNotContainsProduct(Cart cart, Product product) {
        if (!cart.getProducts().contains(product)) {
            try {
                throw new NotFoundException("Cart don't contains product with ID #" + product.getId());
            } catch (NotFoundException e) {
                throw new IllegalArgumentException(e);
            }
        }
    }

    private void checkContainsProduct(Cart cart, Product product) {
        if (cart.getProducts().contains(product)) {
            try {
                throw new NotFoundException("Cart is already contains product with ID #" + product.getId());
            } catch (NotFoundException e) {
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
