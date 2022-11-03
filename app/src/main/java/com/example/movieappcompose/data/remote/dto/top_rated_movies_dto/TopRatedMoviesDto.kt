package com.example.movieappcompose.data.remote.dto.top_rated_movies_dto

data class TopRatedMoviesDto(
    val page: Int,
    val results: List<TopRatedMoviesResultDto>,
    val total_pages: Int,
    val total_results: Int
)