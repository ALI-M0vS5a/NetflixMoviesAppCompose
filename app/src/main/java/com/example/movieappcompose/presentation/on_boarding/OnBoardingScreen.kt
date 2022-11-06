package com.example.movieappcompose.presentation.on_boarding

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.movieappcompose.R
import com.example.movieappcompose.presentation.on_boarding.components.OnBoardingButtons
import com.example.movieappcompose.presentation.on_boarding.components.TopRatedMoviesItem
import com.example.movieappcompose.util.Screen
import com.example.movieappcompose.util.UiEvent
import kotlinx.coroutines.flow.collectLatest

@ExperimentalMaterialApi
@Composable
fun OnBoardingScreen(
    viewModel: OnBoardingViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current
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
                is UiEvent.ShowMovieTitle -> {
                    Toast.makeText(
                        context,
                        event.title,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TopSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 40.dp, end = 15.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                TopRatedMoviesSection()
            }
            if (viewModel.state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 250.dp)
                )
            }
            if (viewModel.state.isLoadingFromPaging) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(top = 250.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(55.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                MoveForwardSection(
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(20.dp))
                DotsIndicator(
                    selectedColor = Color(0xFFC2C6C5),
                    unSelectedColor = Color(0xFF5A595C)
                )
                Spacer(modifier = Modifier.height(100.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    OnBoardingButtons(
                        modifier = Modifier,
                        text = stringResource(id = R.string.sign_up),
                        onClick = {

                        }
                    )
                    OnBoardingButtons(
                        modifier = Modifier,
                        text = stringResource(id = R.string.login_in),
                        borderColor = Color(0xFF9F2628),
                        backgroundColor = Color(0xFF9F2628),
                        onClick = {
                            navController.navigate(Screen.HomeScreen.route)
                        }
                    )
                }

            }
        }
    }
}


@Composable
fun TopSection(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(context)
                    .data("https://image.tmdb.org/t/p/original/wwemzKWzjKYJFfCeiB57q3r4Bcm.svg")
                    .decoderFactory(SvgDecoder.Factory())
                    .build()
            ),
            contentDescription = stringResource(id = R.string.netflix_logo),
            modifier = Modifier
                .size(110.dp)
        )

        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.privacy),
                fontSize = 16.sp,
                color = Color(0XFFC2C6C5)
            )
            Spacer(modifier = Modifier.width(1.dp))
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(id = R.string.more_vert),
                    tint = Color(0XFFC2C6C5)
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun TopRatedMoviesSection(
    viewModel: OnBoardingViewModel = hiltViewModel()
) {
    LazyRow {
        viewModel.state.listOfTopRatedMoviesItem.let { results ->
            items(results.size) { i ->
                Spacer(modifier = Modifier.width(35.dp))
                val topRatedMoviesResult = results[i]
                if(i >= results.size - 1 && !viewModel.state.endReached && !viewModel.state.isLoading) {
                    viewModel.loadNextItems()
                }
                TopRatedMoviesItem(
                    topRatedMoviesResult = topRatedMoviesResult,
                    modifier = Modifier
                        .height(280.dp)
                        .width(200.dp),
                    onItemClick = {
                        viewModel.onEvent(OnBoardingEvent.OnMovieClick(topRatedMoviesResult.original_title))
                    }
                )
            }
        }
    }
}

@Composable
fun MoveForwardSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            modifier = Modifier
                .size(50.dp)
                .border(
                    width = 1.dp,
                    color = Color(0XFFC2C6C5),
                    shape = CircleShape
                ),
            onClick = {

            }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = stringResource(id = R.string.arrow_forward),
                modifier = Modifier
            )
        }
        Spacer(modifier = Modifier.height(35.dp))
        Text(
            text = stringResource(id = R.string.See_whats_next),
            fontSize = 20.sp,
            color = Color(0XFFC2C6C5),
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = stringResource(id = R.string.watch_shows),
            fontSize = 14.sp,
            color = Color(0XFFC2C6C5)
        )
        Text(
            text = stringResource(id = R.string.watch_shows_2),
            fontSize = 14.sp,
            color = Color(0XFFC2C6C5)
        )
    }
}

@Composable
fun DotsIndicator(
    modifier: Modifier = Modifier,
    selectedColor: Color,
    unSelectedColor: Color
) {
    LazyRow(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight()
    ) {
        items(3) { index ->
            when (index) {
                0 -> {
                    Box(
                        modifier = modifier
                            .size(height = 5.dp, width = 15.dp)
                            .background(selectedColor)
                            .border(
                                width = 1.dp,
                                color = Color.Black
                            )
                    )
                }
                else -> {
                    Box(
                        modifier = modifier
                            .size(height = 5.dp, width = 15.dp)
                            .background(unSelectedColor)
                            .border(
                                width = 1.dp,
                                color = Color.Black
                            )
                    )
                }
            }
        }
    }
}
