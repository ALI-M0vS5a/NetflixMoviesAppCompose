package com.example.movieappcompose.presentation.home


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movieappcompose.R
import com.example.movieappcompose.presentation.home.components.HorizontalPagerItem
import com.example.movieappcompose.presentation.home.components.IconOrText
import com.example.movieappcompose.presentation.home.components.IconOrTextList
import com.example.movieappcompose.util.UiEvent
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.collectLatest

@ExperimentalPagerApi
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    var selectedTabIndex by remember {
        mutableStateOf(0)
    }
    val context = LocalContext.current
    val pagerState = rememberPagerState()
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
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
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val configuration = LocalConfiguration.current
        val screenHeight = configuration.screenHeightDp.dp
        val screenWidth = configuration.screenWidthDp.dp

        Box(
            modifier = Modifier
                .width(screenWidth)
                .height(screenHeight / 2)

        ) {
                HorizontalPager(
                    count = viewModel.state.listOfUpcomingMovies.size,
                    modifier = Modifier.fillMaxSize(),
                    state = pagerState
                ) { page ->
                    if(page >= viewModel.state.listOfUpcomingMovies.size - 1 && !viewModel.state.endReached && !viewModel.state.isLoading) {
                        viewModel.loadNextItems()
                    }
                    HorizontalPagerItem(
                        upcomingMoviesResult = viewModel.state.listOfUpcomingMovies[page],
                        modifier = Modifier
                            .fillMaxSize()

                    )
                }

            TabView(
                IconOrText = IconOrTextList,
                onTabSelected = {
                    selectedTabIndex = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
            )
            when (selectedTabIndex) {
                0 -> {

                }
                1 -> {

                }
                2 -> {

                }
            }
            if(viewModel.state.isMoviesTab) {
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.letter_n),
                        contentDescription = stringResource(id = R.string.letter_n_logo),
                        modifier = Modifier
                            .size(50.dp),
                        contentScale = ContentScale.Crop
                    )
                    Column(
                        modifier = Modifier,
                    ) {
                        Text(
                            text = stringResource(id = R.string.watch_now),
                            color = Color.LightGray,
                            fontSize = 14.sp
                        )
                        viewModel.state.listOfUpcomingMovies.let { list ->
                            Text(
                                text = "Watch ${list[pagerState.currentPage].original_title}",
                                color = Color.White,
                                fontSize = 18.sp
                            )
                        }
                    }
                }

            }
            if (viewModel.state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            if(viewModel.state.isLoadingFromPaging) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
        }
    }
}

@Composable
fun TabView(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = hiltViewModel(),
    IconOrText: List<IconOrText>,
    onTabSelected: (selectedIndex: Int) -> Unit
) {
    var selectedTabIndex by remember {
        mutableStateOf(0)
    }
    val inactiveColor = Color.Gray
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconButton(
            onClick = {
                viewModel.onEvent(HomeScreenEvent.OnSearchClick)
            }
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(id = R.string.search)
            )
        }
        IconOrText.forEachIndexed { index, iconOrText ->
            when(index) {
                0 -> {
                    Text(
                        text = iconOrText.title.asString(),
                        color = if (selectedTabIndex == 0) Color.White else inactiveColor,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .clickable {
                                selectedTabIndex = 0
                                onTabSelected(index)
                                viewModel.onEvent(HomeScreenEvent.OnMoviesTabClick)
                            }
                    )
                }
                1 -> {
                    Text(
                        text = iconOrText.title.asString(),
                        color = if (selectedTabIndex == index) Color.White else inactiveColor,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .clickable {
                                selectedTabIndex = index
                                onTabSelected(index)
                                viewModel.onEvent(HomeScreenEvent.OnTVShowsClick)
                            }
                    )
                }
                2 -> {
                    Text(
                        text = iconOrText.title.asString(),
                        color = if (selectedTabIndex == index) Color.White else inactiveColor,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .clickable {
                                selectedTabIndex = index
                                onTabSelected(index)
                                viewModel.onEvent(HomeScreenEvent.OnTrailersClick)
                            }
                    )
                }
            }

        }
    }
}

@Composable
fun PopularMoviesSection(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {

    }
}

