package com.example.movieappcompose.domain.model.movie_details

data class MovieDetails(
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
    val vote_average: Double,
    val vote_count: Int,
    val genres: List<Genre>,
)
