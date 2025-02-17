package com.user.web;

import com.user.client.ProductClient;
import com.user.model.User;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@Slf4j
@RestController
@RequestMapping("/users")
public class UsersController {

  @Autowired
  private ProductClient productClient;
  private List<User> users;

  @PostConstruct
  public void init() {
    users = List.of(
        new User(1, "James", "Wilson", null),
        new User(2, "Maria", "Garcia", null),
        new User(3, "Robert", "Chen", null),
        new User(4, "Sarah", "Patel", null)
    );
  }

  @GetMapping
  public List<User> getUsers() {
    log.info("Getting all users");
    return users.stream()
                .map(x -> {
                  x.setProducts(List.of(productClient.getProduct(getRandomValues())));
                  return x;
                })
                .toList();
  }

  @GetMapping("/{id}")
  public User getProductById(@PathVariable("id") long id) {
    log.info("Getting user by id: {}", id);
    return users.stream()
                .filter(x -> x.getId() == id)
                .map(x -> {
                  x.setProducts(List.of(productClient.getProduct(getRandomValues())));
                  return x;
                })
                .findAny()
                .orElse(null);
  }

  private Long getRandomValues() {
    return new Random().nextLong(1, 7);
  }

}
