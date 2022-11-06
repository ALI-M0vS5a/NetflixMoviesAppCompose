package com.example.movieappcompose.presentation.home.components

import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.movieappcompose.R
import com.example.movieappcompose.domain.model.up_coming.UpcomingMoviesResult


@Composable
fun HorizontalPagerItem(
    modifier: Modifier = Modifier,
    upcomingMoviesResult: UpcomingMoviesResult
) {
    val context = LocalContext.current
    val image = "https://image.tmdb.org/t/p/original${upcomingMoviesResult.poster_path}"
    Box(modifier = modifier) {
        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(context)
                    .data(image)
                    .crossfade(true)
                    .build()
            ),
            contentDescription = stringResource(id = R.string.up_coming_movies_poster),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(alpha = 0.99f)
                .drawWithCache {
                    onDrawWithContent {
                        drawContent()
                        drawRect(brush = Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                Color(0xFF0B0D12)
                            )
                        ), blendMode = BlendMode.SrcAtop)
                    }
                }
        )
    }
}