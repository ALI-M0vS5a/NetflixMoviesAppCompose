package com.example.movieappcompose.presentation.on_boarding

import com.example.movieappcompose.domain.model.top_rated.TopRatedMovies


data class OnBoardingState(
    val listOfTopRatedMovies: TopRatedMovies = TopRatedMovies(results = emptyList()),
    val isLoading: Boolean = false,
    val onMovieClick: String = ""
)
