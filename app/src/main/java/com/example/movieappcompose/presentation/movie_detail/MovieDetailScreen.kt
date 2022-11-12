package com.example.movieappcompose.presentation.movie_detail

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movieappcompose.R
import com.example.movieappcompose.util.UiEvent
import com.example.movieappcompose.util.toHourMinutesSeconds
import com.example.movieappcompose.util.withSuffix
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.flow.collectLatest


@Composable
fun MovieDetailScreen(
    viewModel: MovieDetailScreenViewModel = hiltViewModel()
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
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = viewModel.state.isRefreshing
    )
    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = {
            viewModel.onEvent(MovieDetailEvent.Refresh)
        }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    YouTubePlayer(
                        modifier = Modifier
                    )
                    if(viewModel.state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = Color(0xFF232731),
                            shape = CircleShape.copy(
                                topStart = CornerSize(50.dp),
                                topEnd = CornerSize(50.dp),
                                bottomStart = CornerSize(0.dp),
                                bottomEnd = CornerSize(0.dp)
                            )
                        )
                ) {
                    MovieDetail()
                    Review(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .offset(
                                x = (-40).dp,
                                y = (-30).dp
                            )
                    )
                    if(viewModel.state.isLoadingDetail) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center),

                            )
                    }
                }
            }
        }
    }

}

@Composable
fun YouTubePlayer(
    modifier: Modifier = Modifier,
    viewModel: MovieDetailScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state
    var key by remember {
        mutableStateOf("")
    }
    state.listOfMovie.forEachIndexed { index, movieVideoResult ->
        if(index == 0) {
            key = movieVideoResult.key
        }
    }
    AndroidView(
        factory = { context ->
            YouTubePlayerView(context).apply {
                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        val videoId = key
                        youTubePlayer.loadVideo(videoId, 0F)
                    }
                })
            }
        },
        modifier = modifier
    )
}

@Composable
fun MovieDetail(
    viewModel: MovieDetailScreenViewModel = hiltViewModel()
) {
    val movieDetail = viewModel.state.MovieDetail
    LazyColumn(contentPadding = PaddingValues(25.dp)) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.75f)
            ) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = movieDetail?.original_title ?: "",
                        fontSize = 24.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "(${movieDetail?.release_date?.take(4)})",
                        fontSize = 26.sp,
                        color = Color.Gray,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
                Spacer(modifier = Modifier.height(7.dp))
                Text(
                    text = movieDetail?.tagline ?: "",
                    fontSize = 17.sp,
                    color = Color.Gray,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color.Transparent,
                                shape = RoundedCornerShape(25)
                            )
                            .border(
                                width = 1.dp,
                                color = Color.White,
                                shape = RoundedCornerShape(25)
                            )
                            .width(48.dp)
                            .height(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "16+"
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color.Gray,
                                shape = RoundedCornerShape(25)
                            )
                            .border(
                                width = 1.dp,
                                color = Color.Gray,
                                shape = RoundedCornerShape(25)
                            )
                            .width(48.dp)
                            .height(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "HD"
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.StarRate,
                            contentDescription = stringResource(id = R.string.star_rate),
                            tint = Color.Yellow
                        )
                        Text(
                            text = movieDetail?.vote_average.toString()
                        )
                    }
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(40.dp))
            InteractionSection(
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        item {
            Spacer(modifier = Modifier.height(40.dp))
            Description(
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        item {
            Spacer(modifier = Modifier.height(30.dp))
            Overview(
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun Review(
    modifier: Modifier = Modifier,
    viewModel: MovieDetailScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val review = state.MovieDetail?.vote_count?.let { withSuffix(it.toLong()) }
    Box(
        modifier = modifier
            .width(70.dp)
            .height(130.dp)
            .background(
                color = Color.Red,
                shape = CircleShape
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedButton(
                modifier = modifier
                    .size(60.dp)
                    .offset(
                        x = 39.dp,
                        y = 35.dp
                    ),
                onClick = {

                },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Gray
                )
            ) {
                Icon(
                    imageVector = Icons.Default.StarRate,
                    contentDescription = stringResource(id = R.string.star_rate),
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.height(25.dp))
            Text(
                text = "($review)",
                fontSize = 12.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(id = R.string.reviews),
                fontSize = 12.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun InteractionSection(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconButton(onClick = {  }) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_to_list),
                )
                Text(
                    text = stringResource(id = R.string.add_to_list),
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }
        IconButton(onClick = {  }) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Download,
                    contentDescription = stringResource(id = R.string.download),
                )
                Text(
                    text = stringResource(id = R.string.download),
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }
        IconButton(onClick = {  }) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = stringResource(id = R.string.share),
                )
                Text(
                    text = stringResource(id = R.string.share),
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun Description(
    modifier: Modifier = Modifier,
    viewModel: MovieDetailScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.description),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Row {
            viewModel.state.MovieDetail?.genres?.forEachIndexed { _, genre ->
                Text(
                    text = genre.name + ", "
                )
            }
            Text(text = ".")
            Text(
                text = state.MovieDetail?.runtime?.let { toHourMinutesSeconds(it) }.toString()
            )
        }
    }
}

@Composable
fun Overview(
    modifier: Modifier = Modifier,
    viewModel: MovieDetailScreenViewModel = hiltViewModel()
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.overview),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = viewModel.state.MovieDetail?.release_date ?: ""
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = viewModel.state.MovieDetail?.overview ?: ""
        )
    }
}
