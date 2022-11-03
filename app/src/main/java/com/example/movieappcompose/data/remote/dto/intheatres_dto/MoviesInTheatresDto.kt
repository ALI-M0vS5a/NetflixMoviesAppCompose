package com.example.movieappcompose.data.remote.dto.intheatres_dto

data class MoviesInTheatresDto(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)