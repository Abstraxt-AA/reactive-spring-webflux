package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

class FluxAndMonoGeneratorServiceTest {

    FluxAndMonoGeneratorService service = new FluxAndMonoGeneratorService();

    @Test
    void namesFlux() {

        final var namesFlux = service.namesFlux();
        StepVerifier.create(namesFlux)
                .expectNext("John")
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void mapNamesFlux() {

        final var mapNamesFlux = service.mapNamesFlux();
        StepVerifier.create(mapNamesFlux)
                .expectNext("JOHN", "AHMED", "DILIP")
                .verifyComplete();
    }
}