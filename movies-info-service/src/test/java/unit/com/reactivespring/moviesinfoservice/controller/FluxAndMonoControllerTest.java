package com.reactivespring.moviesinfoservice.controller;

import static org.springframework.test.util.AssertionErrors.*;

import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

@WebFluxTest(controllers = FluxAndMonoController.class)
@AutoConfigureWebTestClient
class FluxAndMonoControllerTest {

    @Autowired
    private WebTestClient client;

    @Test
    void getIntFlux() {

        client.get()
                .uri("/flux")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Integer.class)
                .hasSize(3);
    }

    @Test
    void getIntFlux_SecondApproach() {

        final var response = client.get()
                .uri("/flux")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .returnResult(Integer.class)
                .getResponseBody();
        StepVerifier.create(response)
                .expectNext(1, 2, 3)
                .verifyComplete();
    }

    @Test
    void getIntFlux_ThirdApproach() {

        client.get()
                .uri("/flux")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Integer.class)
                .consumeWith(result -> {
                    final var body = result.getResponseBody();
                    assert (Objects.requireNonNull(body).size()) == 3;
                    for (int i = 0; i < 3; i++) {
                        assertEquals("Not equal", i + 1, body.get(i));
                    }
                });
    }

    @Test
    void getIntMono() {
        client.get()
                .uri("/mono")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(Integer.class)
                .consumeWith(result -> {
                    final var body = result.getResponseBody();
                    assertEquals("Not equal", 1, body);
                });
    }

    @Test
    void streamLong() {
        final var response = client.get()
                .uri("/stream")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .returnResult(Long.class)
                .getResponseBody();
        StepVerifier.create(response)
                .expectNext(0L, 1L, 2L, 3L)
                .thenCancel()
                .verify();
    }
}