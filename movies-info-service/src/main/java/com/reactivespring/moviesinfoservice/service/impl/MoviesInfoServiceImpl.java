package com.reactivespring.moviesinfoservice.service.impl;

import com.reactivespring.moviesinfoservice.domain.MovieInfo;
import com.reactivespring.moviesinfoservice.repository.MovieInfoRepository;
import com.reactivespring.moviesinfoservice.service.MoviesInfoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class MoviesInfoServiceImpl implements MoviesInfoService {

    private MovieInfoRepository movieInfoRepo;

    @Override
    public Mono<MovieInfo> addMovieInfo(MovieInfo movieInfo) {
        return movieInfoRepo.save(movieInfo);
    }

    @Override
    public Flux<MovieInfo> getAllMovieInfos() {
        return movieInfoRepo.findAll();
    }
}
