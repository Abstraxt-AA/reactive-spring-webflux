package com.reactivespring.moviesinfoservice.dto.request;

import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Data
public class CreateMovieInfoRequest {

    private String movieInfoId;
    private String name;
    private Integer year;
    private List<String> cast;
    private LocalDate releaseDate;
}
