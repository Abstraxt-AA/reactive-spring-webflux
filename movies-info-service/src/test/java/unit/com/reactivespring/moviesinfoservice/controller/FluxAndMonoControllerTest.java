package com.reactivespring.moviesinfoservice.controller;

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

        final var response = client.get()
                .uri("/flux")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Integer.class)
                .consumeWith(result -> {
                    final var body = result.getResponseBody();
                    assert (Objects.requireNonNull(body).size()) == 3;
                    for (int i = 0; i < 3; i++) {
                        assert body.get(i) == i + 1;
                    }
                });
    }

    @Test
    void getIntMono() {
    }

    @Test
    void streamLong() {
    }
}