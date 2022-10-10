package com.dkharchenko_hillel.homework7.configurations;

import com.dkharchenko_hillel.homework7.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PersonService personService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] antMatchersForAdmin = {"/all_products", "/all_shops", "/all_carts", "/add_person", "/remove_person",
                "/all_persons", "/add_product", "/remove_product", "/update_product_name", "/update_price", "/add_shop",
                "/remove_shop", "/update_name", "/add_cart", "/remove_cart", "/get_cart", "/add_to_cart",
                "/remove_from_cart", "/remove_all", "/update_first_name", "/update_last_name",
                "/update_phone_number", "/person_carts"};
        String[] antMatchersForCustomer = {"/all_products", "/all_shops",
                "/add_cart", "/remove_cart", "/get_cart", "/add_to_cart", "/remove_from_cart", "/remove_all",
                "/update_first_name", "/update_last_name", "/update_phone_number", "/person_carts"};
        http.headers().frameOptions().disable();
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/main").permitAll()
                .antMatchers("/registration").permitAll()
                .antMatchers("all_shops").permitAll()
                .antMatchers("all_products").permitAll()
                .antMatchers(antMatchersForAdmin).hasRole("ADMIN")
                .antMatchers(antMatchersForCustomer).hasAnyRole("ADMIN", "CUSTOMER")
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/main")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .logoutSuccessUrl("/main");
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personService).passwordEncoder(passwordEncoder);
    }
}
