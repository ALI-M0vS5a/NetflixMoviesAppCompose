package com.example.movieappcompose.di

import com.example.movieappcompose.data.remote.MovieApi
import com.example.movieappcompose.data.repository.MoviesRepositoryImpl
import com.example.movieappcompose.domain.repository.MoviesRepository
import com.example.movieappcompose.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMoviesApi(): MovieApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideMoviesRepository(api: MovieApi): MoviesRepository {
        return MoviesRepositoryImpl(api)
    }
}