package moviesinfoservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class FluxAndMonoController {

    @GetMapping("/flux")
    public Flux<Integer> getIntFlux() {
        return Flux.just(1, 2, 3).log();
    }
}
