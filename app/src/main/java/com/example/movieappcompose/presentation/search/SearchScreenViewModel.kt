package com.example.movieappcompose.presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappcompose.domain.repository.MoviesRepository
import com.example.movieappcompose.util.DefaultPaginator
import com.example.movieappcompose.util.UiEvent
import com.example.movieappcompose.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    var state by mutableStateOf(SearchScreenState())

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    val paginator = DefaultPaginator(
        initialKey = state.page,
        onLoadUpdated = {
            state = state.copy(
                isLoading = it
            )
        },
        onRequest = { nextPage ->
            repository.searchMovies(query = state.onSearchQuery, page = nextPage)
        },
        getNextKey = {
            state.page + 1
        },
        onError = {
            _eventFlow.emit(
                UiEvent.Message(
                    it ?: UiText.unknownError()
                )
            )
        },
        onSuccess = { items, newKey ->
            state = state.copy(
                listSearchResult = state.listSearchResult + items,
                page = newKey,
                endReached = items.isEmpty()
            )
        }
    )
    private var searchJob: Job? = null

    fun onEvent(event: SearchScreenEvent) {
        when (event) {
            is SearchScreenEvent.OnSearch -> {
                state = state.copy(
                    onSearchQuery = event.query
                )
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(1000L)
                    paginator.reset()
                    state = state.copy(
                        page = 1,
                        listSearchResult = emptyList()
                    )
                    loadNextItems()
                }
            }
        }
    }

    fun loadNextItems() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }
}