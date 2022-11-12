package com.example.movieappcompose.presentation.movie_detail


sealed class MovieDetailEvent {
    object Refresh: MovieDetailEvent()
}
