package com.example.movieappcompose.domain.repository

import com.example.movieappcompose.domain.model.most_popular.MostPopularMoviesResult
import com.example.movieappcompose.domain.model.movie_details.MovieDetails
import com.example.movieappcompose.domain.model.movie_video.MovieVideoResult
import com.example.movieappcompose.domain.model.popular_tv_shows.PopularTVShowsResult
import com.example.movieappcompose.domain.model.search_movies.SearchMoviesResult
import com.example.movieappcompose.domain.model.top_rated.TopRatedMoviesResult
import com.example.movieappcompose.domain.model.up_coming.UpcomingMoviesResult
import com.example.movieappcompose.util.Resource
import kotlinx.coroutines.flow.Flow


interface MoviesRepository {
    suspend fun getTopRatedMovies(page: Int): Flow<Resource<List<TopRatedMoviesResult>>>
    suspend fun getUpcomingMovies(page: Int): Flow<Resource<List<UpcomingMoviesResult>>>
    suspend fun getMostPopularMovies(page: Int): Flow<Resource<List<MostPopularMoviesResult>>>
    suspend fun searchMovies(query: String,page: Int): Flow<Resource<List<SearchMoviesResult>>>
    suspend fun getTVShows(page: Int): Flow<Resource<List<PopularTVShowsResult>>>
    suspend fun getVideoById(videoId: Int): Flow<Resource<List<MovieVideoResult>>>
    suspend fun getMovieDetailById(movieId: Int): Flow<Resource<MovieDetails>>
}