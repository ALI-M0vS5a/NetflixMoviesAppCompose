package com.example.movieappcompose.util


sealed class UiEvent {
    data class Message(val uiText: UiText): UiEvent()
    data class ShowMovieTitle(val title: String): UiEvent()
    object OnNavigate: UiEvent()
}
