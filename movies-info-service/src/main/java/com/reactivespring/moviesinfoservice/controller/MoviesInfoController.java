package com.reactivespring.moviesinfoservice.controller;

import com.reactivespring.moviesinfoservice.domain.MovieInfo;
import com.reactivespring.moviesinfoservice.dto.request.CreateMovieInfoRequest;
import com.reactivespring.moviesinfoservice.mapper.MoviesInfoMapper;
import com.reactivespring.moviesinfoservice.service.MoviesInfoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/v1")
public class MoviesInfoController {

    private MoviesInfoService moviesInfoService;
    private MoviesInfoMapper moviesInfoMapper;

    @PostMapping("/movie-infos")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MovieInfo> addMovieInfo(@RequestBody CreateMovieInfoRequest movieInfo) {
        return moviesInfoService.addMovieInfo(moviesInfoMapper.mapToMovieInfo(movieInfo));
    }
}
