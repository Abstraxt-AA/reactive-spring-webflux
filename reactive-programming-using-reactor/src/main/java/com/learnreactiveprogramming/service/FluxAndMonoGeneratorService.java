package com.learnreactiveprogramming.service;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class FluxAndMonoGeneratorService {

    public Flux<String> namesFlux() {

        return Flux.fromIterable(List.of("John", "Ahmed", "Dilip"));
    }

    public static void main(String[] args) {

        final var service = new FluxAndMonoGeneratorService();
        service.namesFlux().subscribe(name -> log.info("Name is: " + name));
    }
}
