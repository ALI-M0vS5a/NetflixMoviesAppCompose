package com.example.movieappcompose.presentation.movie_detail

import com.example.movieappcompose.domain.model.movie_details.MovieDetails
import com.example.movieappcompose.domain.model.movie_video.MovieVideoResult


data class MovieDetailScreenState(
    val isLoading: Boolean = false,
    val listOfMovie: List<MovieVideoResult> = emptyList(),
    val MovieDetail: MovieDetails ?= null,
    val isLoadingDetail: Boolean = false,
    val isRefreshing: Boolean = false
)
