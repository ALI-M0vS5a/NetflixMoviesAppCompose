package com.example.movieappcompose.domain.model.up_coming

import com.example.movieappcompose.data.remote.dto.upcoming_movies_dto.DatesDto


data class UpcomingMovies(
    val dates: Dates,
    val results: List<UpcomingMoviesResult>,
)
