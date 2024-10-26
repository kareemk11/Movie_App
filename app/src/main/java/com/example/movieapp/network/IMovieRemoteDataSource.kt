package com.example.movieapp.network

import com.example.movieapp.model.MovieDetailsResponse
import com.example.movieapp.model.MovieResponse
import kotlinx.coroutines.flow.Flow

interface IMovieRemoteDataSource {
    suspend fun getPopularMovies(page: Int): Flow<MovieResponse>

    suspend fun getNowPlayingMovies(page: Int): Flow<MovieResponse>

    suspend fun getUpcomingMovies(page: Int): Flow<MovieResponse>

    suspend fun getMovieDetails(movieId: Int): Flow<MovieDetailsResponse>

}