package com.example.movieapp.model

import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    suspend fun getPopularMovies(page: Int): Flow<MovieResponse>

    suspend fun getNowPlayingMovies(page: Int): Flow<MovieResponse>

    suspend fun getUpcomingMovies(page: Int): Flow<MovieResponse>

    suspend fun getMovieDetails(movieId: Int): Flow<MovieDetailsResponse>
}