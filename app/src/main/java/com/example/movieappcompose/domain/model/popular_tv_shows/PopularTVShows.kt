package com.example.movieappcompose.domain.model.popular_tv_shows

import com.example.movieappcompose.data.remote.popular_tv_shows_dto.PopularTVShowsResultDto


data class PopularTVShows(
    val results: List<PopularTVShowsResult>
)
