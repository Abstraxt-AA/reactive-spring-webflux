package com.reactivespring.moviesinfoservice.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

@DataMongoTest
@ActiveProfiles("test")
class MovieInfoRepositoryIntegrationTest {

    private final MovieInfoRepository repository;

    public MovieInfoRepositoryIntegrationTest(@Autowired final MovieInfoRepository repository) {
        this.repository = repository;
    }
}