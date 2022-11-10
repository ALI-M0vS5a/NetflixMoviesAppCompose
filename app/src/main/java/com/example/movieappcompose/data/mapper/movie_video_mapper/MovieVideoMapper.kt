package com.example.movieappcompose.data.mapper.movie_video_mapper

import com.example.movieappcompose.data.remote.dto.movie_video_dto.MovieVideoDto
import com.example.movieappcompose.data.remote.dto.movie_video_dto.MovieVideoResultDto
import com.example.movieappcompose.domain.model.movie_video.MovieVideo
import com.example.movieappcompose.domain.model.movie_video.MovieVideoResult


fun MovieVideoDto.toMovieVideo(): MovieVideo {
    return MovieVideo(
        results = results.map { it.toMovieVideoResult() }
    )
}
fun MovieVideoResultDto.toMovieVideoResult(): MovieVideoResult {
    return MovieVideoResult(
        key = key
    )
}