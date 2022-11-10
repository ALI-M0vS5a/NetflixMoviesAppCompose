package com.example.movieappcompose.data.remote.dto.search_movies_dto

data class SearchMoviesDto(
    val page: Int,
    val results: List<SearchMoviesResultDto>,
    val total_pages: Int,
    val total_results: Int
)