package com.example.movieappcompose.presentation.search

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movieappcompose.R
import com.example.movieappcompose.presentation.search.components.SearchResultItem
import com.example.movieappcompose.util.UiEvent
import kotlinx.coroutines.flow.collectLatest

@ExperimentalMaterialApi
@Composable
fun SearchScreen(
    viewModel: SearchScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is UiEvent.Message -> {
                    Toast.makeText(
                        context,
                        event.uiText.asString(context),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> Unit
            }
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            LazyVerticalGrid(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Adaptive(minSize = 128.dp)
            ) {

                items(viewModel.state.listSearchResult.size) { i ->
                    if(i >= viewModel.state.listSearchResult.size - 1 && !viewModel.state.endReached && !viewModel.state.isLoading) {
                        viewModel.loadNextItems()
                    }
                    val movies = viewModel.state.listSearchResult[i]

                    SearchResultItem(
                        searchResult = movies,
                        onItemClick = {

                        },
                        modifier = Modifier
                            .height(215.dp)
                            .width(145.dp)
                            .padding(10.dp),
                    )
                }
            }
        }
        if(viewModel.state.onSearchQuery.isEmpty()) {
            Image(
                imageVector = Icons.Default.SearchOff,
                contentDescription = stringResource(id = R.string.empty_search),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(96.dp)
                    .align(Alignment.Center),
                colorFilter = ColorFilter.tint(color = Color.Red)
            )
        }
        if(viewModel.state.isLoading) {
            if(viewModel.state.page == 1) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            } else {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                )
            }
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    viewModel: SearchScreenViewModel = hiltViewModel()
) {

    Box(modifier = modifier) {
        OutlinedTextField(
            value = viewModel.state.onSearchQuery,
            onValueChange = {
                viewModel.onEvent(SearchScreenEvent.OnSearch(it))
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.White),
            modifier = Modifier
                .fillMaxWidth(),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.hint),
                    color = Color.LightGray,
                    modifier = Modifier,
                    textAlign = TextAlign.Start
                )
            }
        )
    }
}