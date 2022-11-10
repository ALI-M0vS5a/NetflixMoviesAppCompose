package com.example.movieappcompose.presentation.home


import com.example.movieappcompose.domain.model.most_popular.MostPopularMoviesResult
import com.example.movieappcompose.domain.model.up_coming.UpcomingMoviesResult


data class HomeScreenState(
    val listOfUpcomingMovies: List<UpcomingMoviesResult> = emptyList(),
    val listOfMostPopularMovies: List<MostPopularMoviesResult> = emptyList(),
    val isLoadingMostPopularMoviesFromPaging: Boolean = false,
    val isLoadingMostPopularMovies: Boolean = false,
    val isMoviesTab: Boolean = false,
    val isTVShowsTab: Boolean = false,
    val page: Int = 1,
    val endReached: Boolean = false,
    val route: String = ""
)
