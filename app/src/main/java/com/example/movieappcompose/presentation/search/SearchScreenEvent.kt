package com.example.movieappcompose.presentation.search


sealed class SearchScreenEvent {
    data class OnSearch(val query: String): SearchScreenEvent()
}
