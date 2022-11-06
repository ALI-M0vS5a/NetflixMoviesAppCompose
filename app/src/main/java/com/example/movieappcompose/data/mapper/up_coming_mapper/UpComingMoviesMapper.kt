package com.example.movieappcompose.data.mapper.up_coming_mapper

import com.example.movieappcompose.data.remote.dto.upcoming_movies_dto.DatesDto
import com.example.movieappcompose.data.remote.dto.upcoming_movies_dto.UpcomingMoviesDto
import com.example.movieappcompose.data.remote.dto.upcoming_movies_dto.UpcomingMoviesResultDto
import com.example.movieappcompose.domain.model.up_coming.Dates
import com.example.movieappcompose.domain.model.up_coming.UpcomingMovies
import com.example.movieappcompose.domain.model.up_coming.UpcomingMoviesResult

fun UpcomingMoviesDto.toUpcomingMovies(): UpcomingMovies {
    return UpcomingMovies(
        results = results.map { it.toUpcomingMoviesResult() }
    )
}

fun DatesDto.toDates(): Dates {
   return Dates(
        maximum = maximum,
        minimum = minimum
    )
}
fun UpcomingMoviesResultDto.toUpcomingMoviesResult(): UpcomingMoviesResult {
    return UpcomingMoviesResult(
        original_language = original_language,
        original_title = original_title,
        overview = overview,
        popularity = popularity,
        poster_path = poster_path ?: "",
        release_date = release_date,
        title = title,
        vote_average = vote_average,
        vote_count = vote_count
    )
}