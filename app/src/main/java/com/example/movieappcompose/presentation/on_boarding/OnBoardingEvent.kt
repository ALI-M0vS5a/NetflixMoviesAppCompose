package com.example.movieappcompose.presentation.on_boarding


sealed class OnBoardingEvent {
    data class OnMovieClick(val title: String): OnBoardingEvent()
}
