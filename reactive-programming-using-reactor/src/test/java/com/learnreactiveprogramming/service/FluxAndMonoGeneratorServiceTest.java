package com.learnreactiveprogramming.service;

import java.util.List;
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

    @Test
    void immutableNamesFlux() {

        final var mapNamesFlux = service.immutableNamesFlux();
        StepVerifier.create(mapNamesFlux)
                .expectNext("John", "Ahmed", "Dilip")
                .verifyComplete();
    }

    @Test
    void filterNamesFlux() {

        final var mapNamesFlux = service.filterNamesFlux(4);
        StepVerifier.create(mapNamesFlux)
                .expectNext("5 - Ahmed", "5 - Dilip")
                .verifyComplete();
    }

    @Test
    void flatMapNamesFlux() {

        final var mapNamesFlux = service.flatMapNamesFlux(4);
        StepVerifier.create(mapNamesFlux)
                .expectNext("A", "h", "m", "e", "d", "D", "i", "l", "i", "p")
                .verifyComplete();
    }

    @Test
    void delayNamesFlux() {

        final var mapNamesFlux = service.delayNamesFlux(4);
        StepVerifier.create(mapNamesFlux)
                .expectNextCount(10)
                .verifyComplete();
    }

    @Test
    void concatMapNamesFlux() {

        final var mapNamesFlux = service.concatMapNamesFlux(4);
        StepVerifier.create(mapNamesFlux)
                .expectNext("A", "h", "m", "e", "d", "D", "i", "l", "i", "p")
                .verifyComplete();
    }

    @Test
    void nameMono() {

        final var mapNamesFlux = service.nameMono(3);
        StepVerifier.create(mapNamesFlux)
                .expectNext(List.of("J", "o", "h", "n"))
                .verifyComplete();
    }
}