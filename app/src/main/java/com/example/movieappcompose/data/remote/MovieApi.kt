package com.example.movieappcompose.data.remote

import com.example.movieappcompose.data.remote.dto.top_rated_movies_dto.TopRatedMoviesDto
import com.example.movieappcompose.data.remote.dto.upcoming_movies_dto.UpcomingMoviesDto
import com.example.movieappcompose.util.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query


interface MovieApi {

    @GET("/3/movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apikey: String = API_KEY
    ): TopRatedMoviesDto

    @GET("/3/movie/upcoming")
    suspend fun getUpComingMovies(
        @Query("api_key") apikey: String = API_KEY
    ): UpcomingMoviesDto
}