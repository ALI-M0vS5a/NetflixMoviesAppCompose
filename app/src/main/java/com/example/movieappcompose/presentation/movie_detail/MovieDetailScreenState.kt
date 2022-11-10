package com.example.movieappcompose.presentation.movie_detail

import com.example.movieappcompose.domain.model.movie_video.MovieVideoResult


data class MovieDetailScreenState(
    val isLoading: Boolean = false,
    val listOfMovie: List<MovieVideoResult> = emptyList()
)
