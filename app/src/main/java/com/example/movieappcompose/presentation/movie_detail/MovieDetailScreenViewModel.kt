package com.example.movieappcompose.presentation.movie_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class MovieDetailScreenViewModel @Inject constructor(
    private val repository: MoviesRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId = savedStateHandle.get<Int>("movie_id")

    var state by mutableStateOf(MovieDetailScreenState())

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        movieId?.let { movieId ->
            println("movie_id is = $movieId")
            getVideoById(movieId)
        }
    }


    private fun getVideoById(movieId: Int) {
        viewModelScope.launch {
            repository.getVideoById(movieId).collect { result ->
                when(result) {
                    is Resource.Success -> {
                        println("result data = ${result.data}")
                        state = state.copy(
                            listOfMovie = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = true
                        )
                    }
                    is Resource.Error -> {
                        _eventFlow.emit(UiEvent.Message(result.message ?: UiText.unknownError()))
                    }
                }
            }
        }
    }
}