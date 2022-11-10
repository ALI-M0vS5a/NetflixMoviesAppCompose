package com.example.movieappcompose.data.repository

import com.example.movieappcompose.R
import com.example.movieappcompose.data.mapper.most_popular_mapper.toMostPopularMoviesResult
import com.example.movieappcompose.data.mapper.movie_video_mapper.toMovieVideoResult
import com.example.movieappcompose.data.mapper.popular_tv_shows.toPopularTVShowsResult
import com.example.movieappcompose.data.mapper.search_movies.toSearchMoviesResult
import com.example.movieappcompose.data.mapper.top_rated_mapper.toTopRatedMoviesResult
import com.example.movieappcompose.data.mapper.up_coming_mapper.toUpcomingMoviesResult
import com.example.movieappcompose.data.remote.MovieApi
import com.example.movieappcompose.domain.model.most_popular.MostPopularMoviesResult
import com.example.movieappcompose.domain.model.movie_video.MovieVideoResult
import com.example.movieappcompose.domain.model.popular_tv_shows.PopularTVShowsResult
import com.example.movieappcompose.domain.model.search_movies.SearchMoviesResult
import com.example.movieappcompose.domain.model.top_rated.TopRatedMoviesResult
import com.example.movieappcompose.domain.model.up_coming.UpcomingMoviesResult
import com.example.movieappcompose.domain.repository.MoviesRepository
import com.example.movieappcompose.util.Resource
import com.example.movieappcompose.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val api: MovieApi
) : MoviesRepository {
    override suspend fun getTopRatedMovies(page: Int): Flow<Resource<List<TopRatedMoviesResult>>> {
        return flow {
            emit(Resource.Loading())
            val remote = try {
                api.getTopRatedMovies(page = page)
            } catch (e: IOException) {
                emit(
                    Resource.Error(
                        message = UiText.StringResource(
                            resId = R.string.please_check_your_connection
                        )
                    )
                )
                null
            } catch (e: HttpException) {
                emit(
                    Resource.Error(
                        message = (UiText.StringResource(
                            resId = R.string.Oops_something_went_wrong
                        ))
                    )
                )
                null
            }
            remote?.let { topRatedMovies ->
                emit(Resource.Loading())
                emit(Resource.Success(
                    data = topRatedMovies.results.map { it.toTopRatedMoviesResult() }
                ))
            }
        }
    }

    override suspend fun getUpcomingMovies(page: Int): Flow<Resource<List<UpcomingMoviesResult>>> {
        return flow {
            emit(Resource.Loading())
            val remote = try {
                api.getUpComingMovies(page = page)
            } catch (e: IOException) {
                emit(
                    Resource.Error(
                        message = UiText.StringResource(
                            resId = R.string.please_check_your_connection
                        )
                    )
                )
                null
            } catch (e: HttpException) {
                emit(
                    Resource.Error(
                        message = (UiText.StringResource(
                            resId = R.string.Oops_something_went_wrong
                        ))
                    )
                )
                null
            }
            remote?.let { upcomingMovies ->
                emit(Resource.Loading())
                emit(Resource.Success(
                    data = upcomingMovies.results.map { it.toUpcomingMoviesResult() }
                ))
            }
        }
    }

    override suspend fun getMostPopularMovies(page: Int): Flow<Resource<List<MostPopularMoviesResult>>> = flow {
        val remote = try {
            api.getMostPopularMovies(page = page)
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = UiText.StringResource(
                        resId = R.string.please_check_your_connection
                    )
                )
            )
            null
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = (UiText.StringResource(
                        resId = R.string.Oops_something_went_wrong
                    ))
                )
            )
            null
        }
        remote?.let { mostPopular ->
            emit(Resource.Loading())
            emit(Resource.Success(
                data = mostPopular.results.map { it.toMostPopularMoviesResult() }
            ))
        }
    }

    override suspend fun searchMovies(query: String,page: Int): Flow<Resource<List<SearchMoviesResult>>> = flow {
        if(query.isBlank()) {
            emit(Resource.Success(data = emptyList()))
            return@flow
        }
        val remote = try {
            api.searchAllMovies(query = query, page = page)
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = UiText.StringResource(
                        resId = R.string.please_check_your_connection
                    )
                )
            )
            null
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = (UiText.StringResource(
                        resId = R.string.Oops_something_went_wrong
                    ))
                )
            )
            null
        }
        remote?.let { searchResults ->
            emit(Resource.Loading())
            emit(Resource.Success(
                data = searchResults.results.map { it.toSearchMoviesResult() }
            ))
        }
    }

    override suspend fun getTVShows(page: Int): Flow<Resource<List<PopularTVShowsResult>>> = flow {
        val remote = try {
            api.getPopularTvShows(page = page)
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = UiText.StringResource(
                        resId = R.string.please_check_your_connection
                    )
                )
            )
            null
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = (UiText.StringResource(
                        resId = R.string.Oops_something_went_wrong
                    ))
                )
            )
            null
        }
        remote?.let { TVShows ->
            emit(Resource.Loading())
            emit(Resource.Success(
                data = TVShows.results.map { it.toPopularTVShowsResult() }
            ))
        }
    }

    override suspend fun getVideoById(videoId: Int): Flow<Resource<List<MovieVideoResult>>> = flow {
        val remote = try {
            api.getVideoById(movie_id = videoId)
        } catch (e: IOException) {
            emit(
                Resource.Error(
                    message = UiText.StringResource(
                        resId = R.string.please_check_your_connection
                    )
                )
            )
            null
        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = (UiText.StringResource(
                        resId = R.string.Oops_something_went_wrong
                    ))
                )
            )
            null
        }
        remote?.let { video ->
            emit(Resource.Loading())
            emit(Resource.Success(
                data = video.results.map { it.toMovieVideoResult() }
            ))
        }
    }
}
