package com.learnreactiveprogramming.service;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
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
        //noinspection ReactiveStreamsUnusedPublisher
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
                .flatMap(string -> Flux.just(string.split("")))
                .log();
    }

    public Flux<String> delayNamesFlux(int length) {
        return namesFlux()
                .filter(name -> name != null && name.length() > length)
                .flatMap(string -> Flux.just(string.split(""))
                        .delayElements(Duration.ofMillis(random.nextInt(1000))))
                .log();
    }

    public Flux<String> concatMapNamesFlux(int length) {
        return namesFlux()
                .filter(name -> name != null && name.length() > length)
                .concatMap(string -> Flux.just(string.split(""))
                        .delayElements(Duration.ofMillis(random.nextInt(1000))))
                .log();
    }

    public Mono<List<String>> nameMono(int length) {
        return Mono.from(namesFlux())
                .filter(name -> name != null && name.length() > length)
                .flatMap(string -> Mono.just(List.of(string.split(""))))
                .log();
    }

    public Flux<String> flatMapManyNameMono(int length) {
        return nameMono(length)
                .map(list -> list.toArray(String[]::new))
                .flatMapMany(Flux::just)
                .log();
    }

    public Flux<String> mapFilterFlattenNamesFlux(int length) {
        final UnaryOperator<Flux<String>> transformer = flux -> flux.map(String::toUpperCase)
                .filter(name -> name.length() > length)
                .flatMap(name -> Flux.just(name.split("")));
        return namesFlux().transform(transformer).defaultIfEmpty("Φ").log();
    }

    public Flux<String> mapFilterFlattenSwitchIfEmptyNamesFlux(int length) {
        final UnaryOperator<Flux<String>> transformer = flux -> flux.map(String::toUpperCase)
                .filter(name -> name.length() > length)
                .flatMap(name -> Flux.just(name.split("")));
        final var defaultFlux = Flux.just("default").transform(transformer);
        return namesFlux().transform(transformer).switchIfEmpty(defaultFlux).log();
    }

    public Flux<String> exploreConcat() {
        final var abcFlux = Flux.just("A", "B", "C");
        final var defFlux = Flux.just("D", "E", "F");
        return Flux.concat(abcFlux, defFlux).log();
    }

    public Flux<String> exploreConcatWith() {
        final var abcFlux = Flux.just("A", "B", "C");
        final var defFlux = Flux.just("D", "E", "F");
        return abcFlux.concatWith(defFlux);
    }

    public Flux<String> exploreConcatWithMono() {
        final var aMono = Mono.just("A");
        final var bMono = Mono.just("B");
        return aMono.concatWith(bMono);
    }

    public Flux<String> exploreMerge() {
        final var abcFlux = Flux.just("A", "B", "C").delayElements(Duration.ofMillis(100));
        final var defFlux = Flux.just("D", "E", "F").delayElements(Duration.ofMillis(110));
        return Flux.merge(abcFlux, defFlux).log();
    }

    public Flux<String> exploreMergeWith() {
        final var abcFlux = Flux.just("A", "B", "C").delayElements(Duration.ofMillis(100));
        final var defFlux = Flux.just("D", "E", "F").delayElements(Duration.ofMillis(110));
        return abcFlux.mergeWith(defFlux);
    }

    public Flux<String> exploreMergeWithMono() {
        final var aMono = Mono.just("A");
        final var bMono = Mono.just("B");
        return aMono.mergeWith(bMono);
    }

    public Flux<String> exploreMergeSequential() {
        final var abcFlux = Flux.just("A", "B", "C").delayElements(Duration.ofMillis(100));
        final var defFlux = Flux.just("D", "E", "F").delayElements(Duration.ofMillis(110));
        return Flux.mergeSequential(abcFlux, defFlux).log();
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
