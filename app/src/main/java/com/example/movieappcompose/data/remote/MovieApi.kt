package com.example.movieappcompose.data.remote

import com.example.movieappcompose.data.remote.dto.most_popular_dto.MostPopularMoviesDto
import com.example.movieappcompose.data.remote.dto.movie_details_dto.MovieDetailDto
import com.example.movieappcompose.data.remote.dto.movie_video_dto.MovieVideoDto
import com.example.movieappcompose.data.remote.dto.search_movies_dto.SearchMoviesDto
import com.example.movieappcompose.data.remote.dto.top_rated_movies_dto.TopRatedMoviesDto
import com.example.movieappcompose.data.remote.dto.upcoming_movies_dto.UpcomingMoviesDto
import com.example.movieappcompose.data.remote.popular_tv_shows_dto.PopularTVShowsDto
import com.example.movieappcompose.util.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieApi {

    @GET("/3/movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apikey: String = API_KEY,
        @Query("page") page: Int
    ): TopRatedMoviesDto

    @GET("/3/movie/upcoming")
    suspend fun getUpComingMovies(
        @Query("api_key") apikey: String = API_KEY,
        @Query("page") page: Int
    ): UpcomingMoviesDto

    @GET("/3/movie/popular")
    suspend fun getMostPopularMovies(
        @Query("api_key") apikey: String = API_KEY,
        @Query("page") page: Int = 1
    ): MostPopularMoviesDto

    @GET("/3/search/movie")
    suspend fun searchAllMovies(
        @Query("api_key") apikey: String = API_KEY,
        @Query("query") query: String,
        @Query("page") page: Int = 1
    ): SearchMoviesDto

    @GET("/3/tv/popular")
    suspend fun getPopularTvShows(
        @Query("api_key") apikey: String = API_KEY,
        @Query("page") page: Int = 1
    ): PopularTVShowsDto

    @GET("/3/movie/{movie_id}/videos")
    suspend fun getVideoById(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") apikey: String = API_KEY
    ): MovieVideoDto

    @GET("/3/movie/{movie_id}")
    suspend fun getMovieDetailById(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") apikey: String = API_KEY
    ): MovieDetailDto
}