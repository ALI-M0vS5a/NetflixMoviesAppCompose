package com.example.movieappcompose.presentation.on_boarding

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappcompose.domain.repository.MoviesRepository
import com.example.movieappcompose.util.DefaultPaginator
import com.example.movieappcompose.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    var state by mutableStateOf(OnBoardingState())

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val paginator = DefaultPaginator(
        initialKey = state.page,
        onLoadUpdated = { isLoading ->
            state = state.copy(isLoadingFromPaging = isLoading)
        },
        onRequest = { nextPage ->
            repository.getTopRatedMovies(nextPage)
        },
        getNextKey = {
            state.page + 1
        },
        onError = {
            state = state.copy(error = it)
        },
        onSuccess = { items, newKey ->
            state = state.copy(
                listOfTopRatedMoviesItem = state.listOfTopRatedMoviesItem + items,
                page = newKey,
                endReached = items.isEmpty()
            )

        }

    )

    init {
        loadNextItems()
    }

    fun onEvent(event: OnBoardingEvent) {
        when(event) {
            is OnBoardingEvent.OnMovieClick -> {
                viewModelScope.launch {
                    state = state.copy(
                        onMovieClick = event.title
                    )
                    _eventFlow.emit(
                        UiEvent.ShowMovieTitle(
                            state.onMovieClick
                        )
                    )
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

