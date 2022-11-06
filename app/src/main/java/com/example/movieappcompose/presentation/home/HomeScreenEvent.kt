package com.example.movieappcompose.presentation.home


sealed class HomeScreenEvent {
    object OnSearchClick: HomeScreenEvent()
    object OnMoviesTabClick: HomeScreenEvent()
    object OnTVShowsClick: HomeScreenEvent()
    object OnTrailersClick: HomeScreenEvent()
}
