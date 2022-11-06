package com.example.movieappcompose.data.repository

import com.example.movieappcompose.R
import com.example.movieappcompose.data.mapper.top_rated_mapper.toTopRatedMoviesResult
import com.example.movieappcompose.data.mapper.up_coming_mapper.toUpcomingMoviesResult
import com.example.movieappcompose.data.remote.MovieApi
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
}