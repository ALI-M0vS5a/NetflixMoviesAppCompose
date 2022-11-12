package com.example.movieappcompose.data.mapper.movie_detail_mapper

import com.example.movieappcompose.data.remote.dto.movie_details_dto.GenreDto
import com.example.movieappcompose.data.remote.dto.movie_details_dto.MovieDetailDto
import com.example.movieappcompose.domain.model.movie_details.Genre
import com.example.movieappcompose.domain.model.movie_details.MovieDetails

fun MovieDetailDto.toMovieDetails(): MovieDetails {
    return MovieDetails(
        original_language = original_language,
        original_title = original_title,
        overview = overview,
        popularity = popularity,
        poster_path = poster_path,
        release_date = release_date,
        revenue = revenue,
        runtime = runtime,
        status = status,
        tagline = tagline,
        title = title,
        vote_average = vote_average,
        vote_count = vote_count,
        genres = genres.map { it.toGenre() }
    )
}
fun GenreDto.toGenre(): Genre {
    return Genre(
        id = id,
        name = name
    )
}