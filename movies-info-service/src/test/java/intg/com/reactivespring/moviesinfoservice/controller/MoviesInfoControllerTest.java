package com.reactivespring.moviesinfoservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.reactivespring.moviesinfoservice.domain.MovieInfo;
import com.reactivespring.moviesinfoservice.repository.MovieInfoRepository;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MoviesInfoControllerTest {

    private final MovieInfoRepository repository;
    private final WebTestClient webTestClient;
    private static final String MOVIES_INFO_URL = "/v1/movie-infos";

    public MoviesInfoControllerTest(@Autowired final MovieInfoRepository repository,
            @Autowired final WebTestClient webTestClient) {
        this.repository = repository;
        this.webTestClient = webTestClient;
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
    void addMovieInfo() {
        webTestClient
                .post()
                .uri(MOVIES_INFO_URL)
                .bodyValue(new MovieInfo(null, "Batman Begins1", 2005, List.of("Chirstian Bale", "Michael Cane"),
                        LocalDate.parse("2005-06-15")))
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(MovieInfo.class)
                .consumeWith(result -> {
                    final var savedMovieInfo = result.getResponseBody();
                    assert savedMovieInfo != null;
                    assert savedMovieInfo.getMovieInfoId() != null;
                });
    }

    @Test
    void getAllMovieInfos() {
        webTestClient
                .get()
                .uri(MOVIES_INFO_URL)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(MovieInfo.class)
                .hasSize(3);
    }

    @Test
    void getMovieInfoById() {
        webTestClient
                .get()
                .uri(MOVIES_INFO_URL + "/abc")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody(MovieInfo.class)
                .consumeWith(result -> {
                    final var movieInfo = result.getResponseBody();
                    assert movieInfo != null;
                    assertEquals("Dark Knight Rises", movieInfo.getName());
                });
    }
}