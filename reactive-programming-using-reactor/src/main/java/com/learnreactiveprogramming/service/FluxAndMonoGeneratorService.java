package com.learnreactiveprogramming.service;

import java.time.Duration;
import java.util.Random;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class FluxAndMonoGeneratorService {

    private final Random random = new Random();

    public Flux<String> namesFlux() {

        return Flux.just("John", "Ahmed", "Dilip").log();
    }

    public Mono<String> nameMono() {

        return Mono.just("Ahmed");
    }

    public Flux<String> mapNamesFlux() {

        return namesFlux().map(String::toUpperCase).log();
    }

    public Flux<String> immutableNamesFlux() {

        final var namesFlux = namesFlux();
        namesFlux.map(String::toUpperCase);
        return namesFlux;
    }

    public Flux<String> filterNamesFlux(int length) {

        return namesFlux()
                .filter(name -> name != null && name.length() > length)
                .map(name -> name.length() + " - " + name)
                .log();
    }

    public Flux<String> flatMapNamesFlux(int length) {
        return namesFlux()
                .filter(name -> name != null && name.length() > length)
                .flatMap(string -> Flux.fromArray(string.split("")))
                .log();
    }

    public Flux<String> delayNamesFlux(int length) {
        return namesFlux()
                .filter(name -> name != null && name.length() > length)
                .flatMap(string -> Flux.fromArray(string.split(""))
                        .delayElements(Duration.ofMillis(random.nextInt(1000))))
                .log();
    }

    public static void main(String[] args) {

        final Consumer<String> logger = name -> log.info("Name is: " + name);
        final var service = new FluxAndMonoGeneratorService();
        log.debug("Subscribing to namesFlux.");
        service.namesFlux().subscribe(logger);
        log.debug("namesFlux consumed.");
        log.debug("Subscribing to nameMono.");
        service.nameMono().subscribe(logger);
        log.debug("nameMono consumed.");
    }
}
