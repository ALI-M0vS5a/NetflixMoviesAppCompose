package com.example.movieappcompose.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    var TVShowsState by mutableStateOf(TVShowsState())

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val paginator = DefaultPaginator(
        initialKey = state.page,
        onLoadUpdated = {
            state = state.copy(isLoadingMostPopularMoviesFromPaging = it)
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
                isMoviesTab = true,
                isTVShowsTab = false
            )
        }
    )
    private val TVShowsPaginator = DefaultPaginator(
        initialKey = state.page,
        onLoadUpdated = {
            TVShowsState = TVShowsState.copy(isLoading = it)
        },
        onRequest = { nextPage ->
            repository.getTVShows(nextPage)
        },
        getNextKey = {
            TVShowsState.page + 1
        },
        onError = {
            _eventFlow.emit(UiEvent.Message(it ?: UiText.unknownError()))
        },
        onSuccess = { items, newKey ->
            TVShowsState = TVShowsState.copy(
                listOfTVShows = TVShowsState.listOfTVShows + items,
                page = newKey,
                endReached = items.isEmpty()
            )
            state = state.copy(
                isTVShowsTab = true,
                isMoviesTab = false
            )
        }
    )

    init {
        loadNextItems()
        getMostPopularMovies()
        loadNextItemsTVShows()
    }

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.OnSearchClick -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.OnNavigate)
                }
            }
            is HomeScreenEvent.OnMoviesTabClick -> {
                state = state.copy(isMoviesTab = true, isTVShowsTab = false)
            }
            is HomeScreenEvent.OnTVShowsClick -> {
                state = state.copy(isMoviesTab = false, isTVShowsTab = true)
            }
            is HomeScreenEvent.OnTrailersClick -> {
                state = state.copy(
                    isTVShowsTab = false,
                    isMoviesTab = false
                )
            }
        }
    }

    fun loadNextItems() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }
    fun loadNextItemsTVShows() {
        viewModelScope.launch {
            TVShowsPaginator.loadNextItems()
        }
    }

    private fun getMostPopularMovies() {
        viewModelScope.launch {
            repository.getMostPopularMovies(1)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            state = state.copy(
                                listOfMostPopularMovies = result.data ?: emptyList(),
                                isLoadingMostPopularMovies = false
                            )
                        }
                        is Resource.Loading -> {
                            state = state.copy(isLoadingMostPopularMovies = false)
                        }
                        is Resource.Error -> {
                            state = state.copy(isLoadingMostPopularMovies = false)
                            _eventFlow.emit(UiEvent.Message(result.message ?: UiText.unknownError()))
                        }
                    }
                }
        }
    }
}