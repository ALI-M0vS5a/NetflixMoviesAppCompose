package com.example.movieappcompose.presentation.movie_detail.components

import android.media.browse.MediaBrowser.MediaItem
import android.net.Uri


data class VideoItems(
    val contentUri: Uri,
    val mediaItem: MediaItem,
    val name: String
)
