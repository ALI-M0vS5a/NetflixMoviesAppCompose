package com.example.movieappcompose.presentation.on_boarding

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappcompose.domain.model.top_rated.TopRatedMovies
import com.example.movieappcompose.domain.repository.MoviesRepository
import com.example.movieappcompose.util.Resource
import com.example.movieappcompose.util.UiEvent
import com.example.movieappcompose.util.UiText
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

    init {
        getTopRatedMovies()
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
    private fun getTopRatedMovies() {
        viewModelScope.launch {
            repository
                .getTopRatedMovies().collect { result ->
                    when(result) {
                        is Resource.Success -> {
                            state = state.copy(
                                isLoading = false,
                                listOfTopRatedMovies = result.data ?: TopRatedMovies(emptyList())
                            )
                        }
                        is Resource.Error -> {
                            state = state.copy(isLoading = false)
                            _eventFlow.emit(UiEvent.Message(
                                result.message ?: UiText.unknownError()
                            ))
                        }
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoading = true
                            )
                        }
                    }
                }
        }
    }
}