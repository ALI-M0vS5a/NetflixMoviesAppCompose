package com.example.movieappcompose.data.mapper.search_movies

import com.example.movieappcompose.data.remote.dto.search_movies_dto.SearchMoviesDto
import com.example.movieappcompose.data.remote.dto.search_movies_dto.SearchMoviesResultDto
import com.example.movieappcompose.domain.model.search_movies.SearchMovies
import com.example.movieappcompose.domain.model.search_movies.SearchMoviesResult

fun SearchMoviesDto.toSearchMovies(): SearchMovies {
    return SearchMovies(
        results = results.map { it.toSearchMoviesResult() }
    )
}
fun SearchMoviesResultDto.toSearchMoviesResult(): SearchMoviesResult {
    return SearchMoviesResult(
        original_language = original_language,
        original_title = original_title,
        overview = overview,
        popularity = popularity,
        poster_path = poster_path,
        release_date = release_date,
        title = title,
        vote_average = vote_average,
        vote_count = vote_count
    )
}