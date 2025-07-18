package com.product.web;

import com.product.model.Product;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@RestController
@RequestMapping({"/products", "/products/"})
public class ProductController {

    private List<Product> products;
    private List<byte[]> list = new ArrayList<>();

    @PostConstruct
    public void init() {
        products = List.of(
                new Product(1, "Bag"),
                new Product(2, "Shoes"),
                new Product(3, "Mouse"),
                new Product(4, "Keyboard"),
                new Product(5, "T-shirt"),
                new Product(6, "Notebook")
        );
    }

    @GetMapping
    public List<Product> getProducts() {
        log.info("Getting all products");
        return products;
    }

    @GetMapping("/unstable")
    public void unstableEndpoint() {
        log.info("Unstable endpoint invoked");
        long value = new Random().nextLong(1, 10);
        if (value % 2 == 0) {
            throw new RuntimeException("Error here");
        }
    }

    @GetMapping("/out-of-memory/{amount}")
    public void starveMemory(@PathVariable("amount") int amount) {
        log.info("Memory consuming");
        for (int i = 0; i < amount; i++) {
            list.add(new byte[1048576]);
        }
    }


    @GetMapping("/{id}")
    public Product getProductById(@PathVariable("id") long id) {
        log.info("Getting product by id: {}", id);
        return products.stream()
                .filter(x -> x.getId() == id)
                .findAny()
                .orElse(null);
    }

}
