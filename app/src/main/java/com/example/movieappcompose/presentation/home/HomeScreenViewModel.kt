package com.example.movieappcompose.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappcompose.domain.model.up_coming.Dates
import com.example.movieappcompose.domain.model.up_coming.UpcomingMovies
import com.example.movieappcompose.domain.repository.MoviesRepository
import com.example.movieappcompose.util.DefaultPaginator
import com.example.movieappcompose.util.Resource
import com.example.movieappcompose.util.UiEvent
import com.example.movieappcompose.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    var state by mutableStateOf(HomeScreenState())

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val paginator = DefaultPaginator(
        initialKey = state.page,
        onLoadUpdated = {
            state = state.copy(isLoadingFromPaging = it)
        },
        onRequest = { nextPage ->
            repository.getUpcomingMovies(nextPage)
        },
        getNextKey = {
            state.page + 1
        },
        onError = {
            _eventFlow.emit(UiEvent.Message(it ?: UiText.unknownError()))
        },
        onSuccess = { items, newKey ->
            state = state.copy(
                listOfUpcomingMovies = state.listOfUpcomingMovies + items,
                page = newKey,
                endReached = items.isEmpty(),
                isMoviesTab = true
            )
        }
    )

    init {
        getUpcomingMovies()
    }

    fun onEvent(event: HomeScreenEvent) {
        when(event) {
            is HomeScreenEvent.OnSearchClick -> {

            }
            is HomeScreenEvent.OnMoviesTabClick -> {
               getUpcomingMovies()
            }
            is HomeScreenEvent.OnTVShowsClick -> {
                state = state.copy(
                    listOfUpcomingMovies = emptyList(),
                    isMoviesTab = false,
                    isLoading = false
                )
            }
            is HomeScreenEvent.OnTrailersClick -> {
                state = state.copy(
                    listOfUpcomingMovies = emptyList(),
                    isMoviesTab = false,
                    isLoading = false
                )
            }
        }
    }

    fun loadNextItems() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    private fun getUpcomingMovies() {
        viewModelScope.launch {
            repository
                .getUpcomingMovies(1).collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            state = state.copy(
                                isLoading = false,
                                listOfUpcomingMovies = result.data ?: emptyList(),
                                isMoviesTab = true
                            )
                        }
                        is Resource.Loading -> {
                            state = state.copy(isLoading = true)
                        }
                        is Resource.Error -> {
                            state = state.copy(isLoading = false)
                            _eventFlow.emit(
                                UiEvent.Message(
                                    uiText = result.message ?: UiText.unknownError()
                                )
                            )
                        }
                    }
                }
        }
    }
}