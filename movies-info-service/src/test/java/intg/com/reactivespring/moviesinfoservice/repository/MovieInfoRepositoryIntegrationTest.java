package com.reactivespring.moviesinfoservice.repository;

import com.reactivespring.moviesinfoservice.domain.MovieInfo;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DataMongoTest(excludeAutoConfiguration =
        org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration.class)
class MovieInfoRepositoryIntegrationTest {

    private final MovieInfoRepository repository;

    public MovieInfoRepositoryIntegrationTest(@Autowired final MovieInfoRepository repository) {
        this.repository = repository;
    }

    @BeforeEach
    void setUp() {
        final var infos = List.of(new MovieInfo(null, "Batman Begins", 2005, List.of("Chirstian Bale", "Michael Cane"),
                        LocalDate.parse("2005-06-15")),
                new MovieInfo(null, "The Dark Knight", 2008, List.of("Chirstian Bale", "Heath Ledger"),
                        LocalDate.parse("2008-07-18")),
                new MovieInfo("abc", "Dark Knight Rises", 2012, List.of("Chirstian Bale", "Michael Cane"),
                        LocalDate.parse("2012-07-20")));

        repository.saveAll(infos).blockLast();
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll().block();
    }

    @Test
    void findAll() {
        final var moviesInfoFlux = repository.findAll().log();
        StepVerifier.create(moviesInfoFlux)
                .expectNextCount(3)
                .verifyComplete();
    }
}