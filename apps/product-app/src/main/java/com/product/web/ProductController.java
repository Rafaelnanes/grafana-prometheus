package com.product.web;

import com.product.model.Product;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping({"/products", "/products/"})
public class ProductController {

  private List<Product> products;

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

  @GetMapping("/{id}")
  public Product getProductById(@PathVariable("id") long id) {
    log.info("Getting product by id: {}", id);
    return products.stream()
                   .filter(x -> x.getId() == id)
                   .findAny()
                   .orElse(null);
  }

}
