/************************************************************
 * Copyright ©2015-2023 Boutiqaat. All rights reserved
 * —————————————————————————————————
 * NOTICE: All information contained herein is a property of Boutiqaat.
 *************************************************************/

package com.reactivespring.moviesinfoservice.mapper;

import com.reactivespring.moviesinfoservice.domain.MovieInfo;
import com.reactivespring.moviesinfoservice.dto.request.CreateMovieInfoRequest;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = "spring")
public interface MoviesInfoMapper {

    MovieInfo mapToMovieInfo(CreateMovieInfoRequest movieInfoRequest);
}
