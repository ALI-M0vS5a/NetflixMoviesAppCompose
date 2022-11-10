package com.example.movieappcompose.presentation.home

import com.example.movieappcompose.domain.model.popular_tv_shows.PopularTVShowsResult


data class TVShowsState(
    val listOfTVShows: List<PopularTVShowsResult> = emptyList(),
    val isLoading: Boolean = false,
    val endReached: Boolean = false,
    val page: Int = 1
)
