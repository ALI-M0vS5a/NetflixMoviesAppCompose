package com.example.movieappcompose.domain.repository

import com.example.movieappcompose.domain.model.top_rated.TopRatedMovies
import com.example.movieappcompose.domain.model.top_rated.TopRatedMoviesResult
import com.example.movieappcompose.domain.model.up_coming.UpcomingMovies
import com.example.movieappcompose.domain.model.up_coming.UpcomingMoviesResult
import com.example.movieappcompose.util.Resource
import kotlinx.coroutines.flow.Flow


interface MoviesRepository {
    suspend fun getTopRatedMovies(page: Int): Flow<Resource<List<TopRatedMoviesResult>>>
    suspend fun getUpcomingMovies(page: Int): Flow<Resource<List<UpcomingMoviesResult>>>
}