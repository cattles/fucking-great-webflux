package com.example.webflux.controller;

import com.example.webflux.FuckingGreatWebfluxApplicationTests;
import com.example.webflux.domain.User;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.function.Consumer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserControllerTest extends FuckingGreatWebfluxApplicationTests {

    @Test
    void getUser() {
        webTestClient.get().uri("/v1/users/1").exchange().expectStatus().isOk().expectBody(User.class).value(
                (Consumer<User>) user -> Assertions.assertEquals(user.getFirstName(), "Cattle"));

//        StepVerifier.create(this.webTestClient.get().uri("/v1/users/1")
//                .accept(MediaType.APPLICATION_JSON)
//                .exchange()
//                .returnResult(User.class).getResponseBody().timeout(Duration.ofSeconds(60 * 1000L))
//                    ).expectSubscription()
//                .assertNext(u -> {
//                    assertEquals(1L, u.getId(), "Actual userId must match expected");
//                    assertEquals("Cattle", u.getFirstName(), "Actual firstName match expected");
//                    assertEquals("Ma", u.getLastName(), "Actual lastName match expected");
//                }).expectComplete().log().verify();
    }

    @Test
    void createUser() {

        User expected = new User(4L, "WebFlux", "Test");

        // Test creating a new user using the transactional R2DBC client API
        StepVerifier.create(this.webTestClient.post().uri("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(expected), User.class)
                .exchange()
                .returnResult(User.class).getResponseBody()).expectSubscription()
                .assertNext(u -> {
                    assertThat("Actual userId must not be null", u.getId(), Matchers.notNullValue());
                    assertEquals(expected.getFirstName(), u.getFirstName(), "Actual firstName match expected");
                    assertEquals(expected.getLastName(), u.getLastName(), "Actual lastName match expected");
                    expected.setId(u.getId());
                }).expectComplete().log().verify();

        // Test that the transaction was not rolled back
        StepVerifier.create(this.webTestClient.get().uri("/v1/users/" + expected.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(User.class).getResponseBody()).expectSubscription()
                .assertNext(u -> {
                    assertEquals(expected.getId(), u.getId(), "Actual userId must match expected");
                    assertEquals(expected.getFirstName(), u.getFirstName(), "Actual firstName match expected");
                    assertEquals(expected.getLastName(), u.getLastName(), "Actual lastName match expected");
                }).expectComplete().log().verify();
    }


    @Test
    void findUser() {
        webTestClient.get().uri("/v1/users")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class).consumeWith(u -> u.getResponseBody().forEach(System.out::println));
    }
}