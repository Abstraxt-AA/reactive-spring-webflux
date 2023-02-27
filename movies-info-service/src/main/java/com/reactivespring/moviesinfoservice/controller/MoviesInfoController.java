package com.reactivespring.moviesinfoservice.controller;

import com.reactivespring.moviesinfoservice.domain.MovieInfo;
import com.reactivespring.moviesinfoservice.dto.request.CreateMovieInfoRequest;
import com.reactivespring.moviesinfoservice.mapper.MoviesInfoMapper;
import com.reactivespring.moviesinfoservice.service.MoviesInfoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
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

    @GetMapping("/movie-infos")
    public Flux<MovieInfo> getAllMovieInfos() {
        return moviesInfoService.getAllMovieInfos();
    }

    @GetMapping("/movie-infos/{id}")
    public Mono<MovieInfo> getMovieInfoById(@PathVariable String id) {
        return moviesInfoService.getMovieInfoById(id);
    }

    @PutMapping("/movie-infos/{id}")
    public Mono<MovieInfo> updateMovieInfo(@PathVariable String id, @RequestBody CreateMovieInfoRequest movieInfo) {
        return moviesInfoService.updateMovieInfo(id, moviesInfoMapper.mapToMovieInfo(movieInfo));
    }

    @DeleteMapping("/movie-infos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteMovieInfo(@PathVariable String id) {
        return moviesInfoService.deleteMovieInfo(id);
    }
}
