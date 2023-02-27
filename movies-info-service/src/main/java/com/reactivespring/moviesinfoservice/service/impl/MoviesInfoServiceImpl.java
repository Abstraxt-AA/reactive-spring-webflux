package com.reactivespring.moviesinfoservice.service.impl;

import com.reactivespring.moviesinfoservice.domain.MovieInfo;
import com.reactivespring.moviesinfoservice.repository.MovieInfoRepository;
import com.reactivespring.moviesinfoservice.service.MoviesInfoService;
import java.util.Objects;
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

    @Override
    public Mono<MovieInfo> getMovieInfoById(String id) {
        return movieInfoRepo.findById(id);
    }

    @Override
    public Mono<MovieInfo> updateMovieInfo(String id, MovieInfo movieInfo) {
        return getMovieInfoById(id)
                .flatMap(persistedMovieInfo -> {
                    if (Objects.nonNull(movieInfo)) {
                        persistedMovieInfo.setCast(movieInfo.getCast());
                        persistedMovieInfo.setName(movieInfo.getName());
                        persistedMovieInfo.setReleaseDate(movieInfo.getReleaseDate());
                        persistedMovieInfo.setYear(movieInfo.getYear());
                        return addMovieInfo(persistedMovieInfo);
                    } else {
                        return Mono.empty();
                    }
                });
    }

    @Override
    public Mono<Void> deleteMovieInfo(String id) {
        return movieInfoRepo.deleteById(id);
    }
}
