package com.example.movieappcompose.presentation.view_all

import com.example.movieappcompose.domain.model.most_popular.MostPopularMoviesResult


data class ViewAllMoviesState(
    val listOfAllPopularMovies: List<MostPopularMoviesResult> = emptyList(),
    val isLoading: Boolean = false,
    val page: Int = 1,
    val endReached: Boolean = false
)
