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

    @Test
    void flatMapManyNameMono() {
        final var mapNamesFlux = service.flatMapManyNameMono(3);
        StepVerifier.create(mapNamesFlux)
                .expectNext("J", "o", "h", "n")
                .verifyComplete();
    }

    @Test
    void mapFilterFlattenNamesFlux() {
        final var mapNamesFlux = service.mapFilterFlattenNamesFlux(4);
        StepVerifier.create(mapNamesFlux)
                .expectNext("A", "H", "M", "E", "D", "D", "I", "L", "I", "P")
                .verifyComplete();
    }

    @Test
    void mapFilterFlattenNamesFlux_DefaultIfEmpty() {
        final var mapNamesFlux = service.mapFilterFlattenNamesFlux(6);
        StepVerifier.create(mapNamesFlux)
                .expectNext("Φ")
                .verifyComplete();
    }

    @Test
    void mapFilterFlattenSwitchIfEmptyNamesFlux() {
        final var mapNamesFlux = service.mapFilterFlattenSwitchIfEmptyNamesFlux(6);
        StepVerifier.create(mapNamesFlux)
                .expectNext("D", "E", "F", "A", "U", "L", "T")
                .verifyComplete();
    }

    @Test
    void exploreConcat() {
        final var flux = service.exploreConcat();
        StepVerifier.create(flux)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    void exploreConcatWith() {
        final var flux = service.exploreConcatWith();
        StepVerifier.create(flux)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    void exploreConcatWithMono() {
        final var flux = service.exploreConcatWithMono();
        StepVerifier.create(flux)
                .expectNext("A", "B")
                .verifyComplete();
    }

    @Test
    void exploreMerge() {
        final var flux = service.exploreMerge();
        StepVerifier.create(flux)
                .expectNext("A", "D", "B", "E", "C", "F")
                .verifyComplete();
    }

    @Test
    void exploreMergeWith() {
        final var flux = service.exploreMergeWith();
        StepVerifier.create(flux)
                .expectNext("A", "D", "B", "E", "C", "F")
                .verifyComplete();
    }

    @Test
    void exploreMergeWithMono() {
        final var flux = service.exploreMergeWithMono();
        StepVerifier.create(flux)
                .expectNext("A", "B")
                .verifyComplete();
    }

    @Test
    void exploreMergeSequential() {
        final var flux = service.exploreMergeSequential();
        StepVerifier.create(flux)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    void exploreZip() {
        final var flux = service.exploreZip();
        StepVerifier.create(flux)
                .expectNext("AD", "BE", "CF")
                .verifyComplete();
    }

    @Test
    void exploreZipFour() {
        final var flux = service.exploreZipFour();
        StepVerifier.create(flux)
                .expectNext("AD14", "BE25", "CF36")
                .verifyComplete();
    }

    @Test
    void exploreZipWith() {
        final var flux = service.exploreZipWith();
        StepVerifier.create(flux)
                .expectNext("AD", "BE", "CF")
                .verifyComplete();
    }

    @Test
    void exploreMonoZipWith() {
        final var flux = service.exploreMonoZipWith();
        StepVerifier.create(flux)
                .expectNext("AB")
                .verifyComplete();
    }
}