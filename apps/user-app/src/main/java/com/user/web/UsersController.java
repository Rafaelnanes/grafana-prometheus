package com.user.web;

import com.user.client.ProductClient;
import com.user.model.LeakedObject;
import com.user.model.User;
import com.user.service.UserServiceAsync;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("/users")
public class UsersController {

    private final List<LeakedObject> leakedObjects = new ArrayList<>();
    @Autowired
    private ProductClient productClient;
    @Autowired
    private UserServiceAsync userServiceAsync;
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

    @GetMapping("/async")
    public List<User> getUsersAsync() throws ExecutionException, InterruptedException {
        log.info("Getting all users async");
        for (User user : users) {
            user.setProducts(List.of(userServiceAsync.getProductById(getRandomValues()).get()));
        }
        return users;
    }

    @GetMapping("/memory-leak")
    public void memoryLeak() {
        log.info("Memory leaking");
        leakedObjects.add(new LeakedObject());
    }

    @GetMapping("/free-memory")
    public void freeLeak() {
        try {
            log.info("Free memory List Size: {}", leakedObjects.size());
            for (int i = 0; i < 100; i++) {
                leakedObjects.remove(i);
            }
        } catch (Exception e) {
            // do nothing
        }
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
