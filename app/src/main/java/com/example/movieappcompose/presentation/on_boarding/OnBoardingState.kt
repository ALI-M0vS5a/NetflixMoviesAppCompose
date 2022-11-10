package com.example.movieappcompose.presentation.on_boarding


import com.example.movieappcompose.domain.model.top_rated.TopRatedMoviesResult
import com.example.movieappcompose.util.UiText


data class OnBoardingState(
    val listOfTopRatedMoviesItem: List<TopRatedMoviesResult> = emptyList(),
    val isLoadingFromPaging: Boolean = false,
    val error: UiText? = null,
    val endReached: Boolean = false,
    val page: Int = 1,
    val onMovieClick: String = ""
)
