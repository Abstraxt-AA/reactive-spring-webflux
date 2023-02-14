package com.reactivespring.moviesinfoservice.repository;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

import com.reactivespring.moviesinfoservice.domain.MovieInfo;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

@ActiveProfiles("test")
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

    @Test
    void findById() {
        final var moviesInfoMono = repository.findById("abc").log();
        StepVerifier.create(moviesInfoMono)
                .assertNext(movieInfo -> assertEquals("Not equal", "Dark Knight Rises", movieInfo.getName()))
                .verifyComplete();
    }

    @Test
    void save() {
        final var info = new MovieInfo(null, "Batman Begins1", 2005, List.of("Chirstian Bale", "Michael Cane"),
                LocalDate.parse("2005-06-15"));

        final var moviesInfoMono = repository.save(info).log();
        StepVerifier.create(moviesInfoMono)
                .assertNext(movieInfo -> {
                    assertNotNull("Null", movieInfo.getMovieInfoId());
                    assertEquals("Not equal", "Batman Begins1", movieInfo.getName());
                })
                .verifyComplete();
    }

    @Test
    void update() {
        final var moviesInfo = repository.findById("abc").log().block();
        Objects.requireNonNull(moviesInfo).setYear(2021);
        final var moviesInfoMono = repository.save(moviesInfo).log();
        StepVerifier.create(moviesInfoMono)
                .assertNext(movieInfo -> assertEquals("Not equal", 2021, movieInfo.getYear()))
                .verifyComplete();
    }

    @Test
    void delete() {
        repository.deleteById("abc").block();
        final var moviesInfoFlux = repository.findAll().log();
        StepVerifier.create(moviesInfoFlux)
                .expectNextCount(2)
                .verifyComplete();
    }
}