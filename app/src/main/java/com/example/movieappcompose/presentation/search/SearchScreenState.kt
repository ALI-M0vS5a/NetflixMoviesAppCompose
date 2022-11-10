package com.example.movieappcompose.presentation.search

import com.example.movieappcompose.domain.model.search_movies.SearchMoviesResult


data class SearchScreenState(
   val listSearchResult: List<SearchMoviesResult> = emptyList(),
   val isLoading: Boolean = false,
   val page: Int = 1,
   val endReached: Boolean = false,
   val onSearchQuery: String = ""
)
