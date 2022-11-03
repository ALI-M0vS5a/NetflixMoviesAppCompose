package com.example.movieappcompose.data.remote.dto.most_popular_dto

data class MostPopularMoviesDto(
    val page: Int,
    val results: List<MostPopularMoviesResultDto>,
    val total_pages: Int,
    val total_results: Int
)