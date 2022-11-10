package com.example.movieappcompose.presentation.search.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.movieappcompose.R
import com.example.movieappcompose.domain.model.search_movies.SearchMoviesResult

@ExperimentalMaterialApi
@Composable
fun SearchResultItem(
    modifier: Modifier = Modifier,
    searchResult: SearchMoviesResult,
    onItemClick: () -> Unit
) {
    val context = LocalContext.current
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(10)),
        onClick = {
            onItemClick()
        },

        ) {
        Box(modifier = Modifier.fillMaxSize()) {
            val image = "https://image.tmdb.org/t/p/original${searchResult.poster_path}"
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(context)
                        .data(image)
                        .crossfade(true)
                        .build()
                ),
                contentDescription = stringResource(id = R.string.top_rated_movies_poster),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
            StarRatings(
                searchResult = searchResult,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .width(55.dp)
                    .height(35.dp)
            )
            Image(
                imageVector = Icons.Outlined.FavoriteBorder,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 10.dp, end = 20.dp),
                colorFilter = ColorFilter.tint(color = Color.White)
            )
        }
    }
}

@Composable
fun StarRatings(
    modifier: Modifier = Modifier,
    searchResult: SearchMoviesResult
) {
    Box(
        modifier = modifier
            .background(
                color = Color.Gray,
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 20.dp
                )
            )
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = Icons.Default.StarRate,
                contentDescription = stringResource(id = R.string.star_rate),
                colorFilter = ColorFilter.tint(color = Color.Yellow),
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = searchResult.vote_average.toString()
            )
        }
    }
}