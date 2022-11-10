package com.example.movieappcompose.data.mapper.most_popular_mapper

import com.example.movieappcompose.data.remote.dto.most_popular_dto.MostPopularMoviesDto
import com.example.movieappcompose.data.remote.dto.most_popular_dto.MostPopularMoviesResultDto
import com.example.movieappcompose.domain.model.most_popular.MostPopularMovies
import com.example.movieappcompose.domain.model.most_popular.MostPopularMoviesResult

fun MostPopularMoviesDto.toMostPopularMovies(): MostPopularMovies {
    return MostPopularMovies(
        results = results.map { it.toMostPopularMoviesResult() }
    )
}

fun MostPopularMoviesResultDto.toMostPopularMoviesResult(): MostPopularMoviesResult {
    return MostPopularMoviesResult(
        original_title = original_title,
        popularity = popularity,
        poster_path = poster_path,
        vote_count = vote_count,
        vote_average = vote_average,
        id = id
    )
}