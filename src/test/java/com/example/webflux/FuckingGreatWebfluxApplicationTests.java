package com.example.webflux;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import javax.annotation.Resource;
import java.time.Duration;

@SpringBootTest(classes = FuckingGreatWebfluxApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class FuckingGreatWebfluxApplicationTests {

    @Resource
    protected WebTestClient webTestClient;

    @BeforeEach
    void contextLoads() {
        webTestClient = webTestClient
                .mutate()
                .responseTimeout(Duration.ofMillis(30 * 1000L))
                .build();
    }


}
