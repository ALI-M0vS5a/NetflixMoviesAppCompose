package com.example.movieappcompose.domain.model.most_popular


data class MostPopularMoviesResult(
    val original_title: String,
    val popularity: Double,
    val poster_path: String,
    val vote_average: Double,
    val vote_count: Int
)
