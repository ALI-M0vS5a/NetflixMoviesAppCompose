package com.example.movieappcompose.presentation.home


import com.example.movieappcompose.domain.model.up_coming.UpcomingMoviesResult


data class HomeScreenState(
    val listOfUpcomingMovies: List<UpcomingMoviesResult> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingFromPaging: Boolean = false,
    val isMoviesTab: Boolean = false,
    val page: Int = 1,
    val endReached: Boolean = false
)
