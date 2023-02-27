package com.reactivespring.moviesinfoservice.service;

import com.reactivespring.moviesinfoservice.domain.MovieInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MoviesInfoService {

    Mono<MovieInfo> addMovieInfo(MovieInfo movieInfo);

    Flux<MovieInfo> getAllMovieInfos();

    Mono<MovieInfo> getMovieInfoById(String id);
}
