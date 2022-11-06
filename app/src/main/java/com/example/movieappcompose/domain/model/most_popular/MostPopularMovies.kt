package com.example.movieappcompose.domain.model.most_popular

import com.example.movieappcompose.data.remote.dto.most_popular_dto.MostPopularMoviesResultDto


data class MostPopularMovies(
    val results: List<MostPopularMoviesResult>,
)
