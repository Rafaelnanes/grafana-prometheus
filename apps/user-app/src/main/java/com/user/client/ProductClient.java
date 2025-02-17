package com.user.client;

import com.user.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "product-service", url = "${product.service.url}")
public interface ProductClient {
  @GetMapping("/products")
  List<Product> getAllProducts();

  @GetMapping("/products/{id}")
  Product getProduct(@PathVariable("id") Long id);
}
