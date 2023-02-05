package com.learnreactiveprogramming.service;

import java.util.List;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class FluxAndMonoGeneratorService {

    public Flux<String> namesFlux() {

        return Flux.fromIterable(List.of("John", "Ahmed", "Dilip"));
    }

    public Mono<String> nameMono() {

        return Mono.just("Ahmed");
    }

    public static void main(String[] args) {

        final Consumer<String> logger = name -> log.info("Name is: " + name);
        final var service = new FluxAndMonoGeneratorService();
        log.trace("Subscribing to namesFlux.");
        service.namesFlux().subscribe(logger);
        log.trace("namesFlux consumed.");
        log.trace("Subscribing to nameMono.");
        service.nameMono().subscribe(logger);
        log.trace("nameMono consumed.");
    }
}
