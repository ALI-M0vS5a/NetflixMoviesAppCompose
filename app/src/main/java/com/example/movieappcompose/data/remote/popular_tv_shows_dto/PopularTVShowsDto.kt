package com.example.movieappcompose.data.remote.popular_tv_shows_dto

data class PopularTVShowsDto(
    val page: Int,
    val results: List<PopularTVShowsResultDto>,
    val total_pages: Int,
    val total_results: Int
)