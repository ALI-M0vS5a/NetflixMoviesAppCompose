package com.example.movieappcompose.data.remote.dto.upcoming_movies_dto

data class UpcomingMoviesDto(
    val dates: DatesDto,
    val page: Int,
    val results: List<UpcomingMoviesResultDto>,
    val total_pages: Int,
    val total_results: Int
)