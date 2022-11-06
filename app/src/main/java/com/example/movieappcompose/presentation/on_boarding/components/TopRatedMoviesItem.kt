package com.example.movieappcompose.presentation.on_boarding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.movieappcompose.R
import com.example.movieappcompose.domain.model.top_rated.TopRatedMoviesResult

@ExperimentalMaterialApi
@Composable
fun TopRatedMoviesItem(
    modifier: Modifier = Modifier,
    topRatedMoviesResult: TopRatedMoviesResult,
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
        val image = "https://image.tmdb.org/t/p/original${topRatedMoviesResult.poster_path}"
        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(context)
                    .data(image)
                    .crossfade(true)
                    .build()
            ),
            contentDescription = stringResource(id = R.string.top_rated_movies_poster),
            contentScale = ContentScale.Crop
        )
    }
}