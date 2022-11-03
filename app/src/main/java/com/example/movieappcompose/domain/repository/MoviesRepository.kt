package com.example.movieappcompose.domain.repository

import com.example.movieappcompose.domain.model.top_rated.TopRatedMovies
import com.example.movieappcompose.util.Resource
import kotlinx.coroutines.flow.Flow


interface MoviesRepository {
    suspend fun getTopRatedMovies(): Flow<Resource<TopRatedMovies>>
}