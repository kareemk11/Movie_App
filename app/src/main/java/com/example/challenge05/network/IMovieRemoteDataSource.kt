package com.example.challenge05.network

import com.example.challenge05.model.MovieDetailsResponse
import com.example.challenge05.model.MovieResponse
import kotlinx.coroutines.flow.Flow

interface IMovieRemoteDataSource {
    suspend fun getPopularMovies(page: Int): Flow<MovieResponse>

    suspend fun getNowPlayingMovies(page: Int): Flow<MovieResponse>

    suspend fun getUpcomingMovies(page: Int): Flow<MovieResponse>

    suspend fun getMovieDetails(movieId: Int): Flow<MovieDetailsResponse>

}