package com.example.movieappcompose.presentation.home


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
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
import androidx.navigation.NavController
import com.example.movieappcompose.R
import com.example.movieappcompose.presentation.home.components.*
import com.example.movieappcompose.util.Screen
import com.example.movieappcompose.util.UiEvent
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.collectLatest

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    navController: NavController
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
                is UiEvent.OnNavigate -> {
                    navController.navigate(Screen.SearchScreen.route)
                }
                else -> Unit
            }
        }
    }
    var spacingForLoading by remember {
        mutableStateOf(0.dp)
    }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .width(screenWidth)
                        .height(screenHeight / 2)

                ) {
                    if(viewModel.state.isMoviesTab) {
                        HorizontalPager(
                            count = viewModel.state.listOfUpcomingMovies.size,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(end = spacingForLoading),
                            state = pagerState
                        ) { page ->
                            if (page >= viewModel.state.listOfUpcomingMovies.size - 1 && !viewModel.state.endReached && !viewModel.state.isLoadingMostPopularMoviesFromPaging) {
                                viewModel.loadNextItems()
                            }
                            HorizontalPagerItem(
                                upcomingMoviesResult = viewModel.state.listOfUpcomingMovies[page],
                                modifier = Modifier
                                    .fillMaxSize()

                            )
                        }
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
                        PlayButton(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(end = 35.dp)
                                .size(60.dp)
                                .offset(y = (45).dp),
                            onClick = {
                                navController.navigate(Screen.MovieDetailScreen.route)
                            }
                        )
                    }
                    if (viewModel.state.isMoviesTab) {
                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(start = 15.dp, bottom = 15.dp),
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
                    if (viewModel.state.isLoadingMostPopularMoviesFromPaging) {
                        if (viewModel.state.page == 1) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        } else {
                            spacingForLoading = 150.dp
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.CenterEnd)
                            )
                        }
                    } else {
                        spacingForLoading = 0.dp
                    }
                }
                Spacer(modifier = Modifier.height(75.dp))
                if (viewModel.state.isMoviesTab) {
                    Box(
                        modifier = Modifier
                            .width(screenWidth)
                            .height(screenHeight / 2)
                    ) {
                        PopularMoviesSection(
                            onViewAllClick = {
                                navController.navigate(Screen.ViewAllPopularMoviesScreen.route)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }
            }
    }
    if(viewModel.state.isTVShowsTab) {
        Box(
            modifier = Modifier
                .width(screenWidth)
                .height(screenHeight / 2)

        ) {
            LazyRow(modifier = Modifier.align(Alignment.Center).padding(top = 50.dp)) {
                viewModel.TVShowsState.listOfTVShows.let { results ->
                    items(results.size) { i ->
                        Spacer(modifier = Modifier.width(35.dp))
                        TVShowsItem(
                            TVShows = results[i],
                            modifier = Modifier
                                .height(215.dp)
                                .width(145.dp),
                            onItemClick = {

                            }
                        )
                    }
                }
            }
            if (viewModel.TVShowsState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
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
            when (index) {
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

@ExperimentalMaterialApi
@Composable
fun PopularMoviesSection(
    modifier: Modifier = Modifier,
    onViewAllClick: () -> Unit,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.popular_movies),
                color = Color.White,
                fontSize = 18.sp
            )
            Text(
                text = stringResource(id = R.string.view_all),
                color = Color.LightGray,
                fontSize = 14.sp,
                modifier = Modifier
                    .clickable {
                        onViewAllClick()
                    }
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Box(modifier = Modifier.fillMaxWidth()) {
            LazyRow(modifier = Modifier) {
                viewModel.state.listOfMostPopularMovies.let { results ->
                    items(results.size) { i ->
                        Spacer(modifier = Modifier.width(35.dp))
                        MostPopularMoviesItem(
                            mostPopularMovies = results[i],
                            modifier = Modifier
                                .height(215.dp)
                                .width(145.dp),
                            onItemClick = {

                            }
                        )
                    }
                }
            }
            if (viewModel.state.isLoadingMostPopularMovies) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun PlayButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier,
        onClick = {
            onClick()
        },
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Red,
            contentColor = Color.White
        )
    ) {
        Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = stringResource(id = R.string.play)
        )
    }
}

