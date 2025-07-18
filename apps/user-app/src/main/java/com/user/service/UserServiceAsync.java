package com.user.service;

import com.user.client.ProductClient;
import com.user.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class UserServiceAsync {
    @Autowired
    private ProductClient productClient;

    @Async
    public CompletableFuture<Product> getProductById(long id) {
        log.info("Querying for product: {} ", id);
        try {
            Thread.sleep(Duration.ofSeconds(1));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return CompletableFuture.completedFuture(productClient.getProduct(id))
                .thenApply(x -> x);
    }


}
