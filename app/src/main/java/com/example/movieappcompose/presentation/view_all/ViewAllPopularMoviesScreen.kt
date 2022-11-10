package com.example.movieappcompose.presentation.view_all

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.movieappcompose.presentation.home.components.MostPopularMoviesItem
import com.example.movieappcompose.util.Screen

@ExperimentalMaterialApi
@Composable
fun ViewAllPopularMoviesScreen(
    viewModel: ViewAllPopularMoviesViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state
    var loadingSpacing by remember {
        mutableStateOf(0.dp)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Adaptive(minSize = 128.dp)
        ) {

            items(state.listOfAllPopularMovies.size) { i ->
                if(i >= state.listOfAllPopularMovies.size - 1 && !state.endReached && !state.isLoading) {
                    viewModel.loadNextItems()
                }
                val movies = state.listOfAllPopularMovies[i]

                MostPopularMoviesItem(
                    mostPopularMovies = movies,
                    onItemClick = {
                        navController.navigate(Screen.MovieDetailScreen.route + "?movie_id=${movies.id}")
                    },
                    modifier = Modifier
                        .height(215.dp)
                        .width(145.dp)
                        .padding(10.dp),
                )
            }
        }
        if(state.isLoading) {
            if(state.page == 1) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            } else {
                loadingSpacing = 50.dp
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                )
            }
        } else {
            loadingSpacing = 0.dp
        }
    }
}