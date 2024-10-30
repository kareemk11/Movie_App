package com.example.challenge05

import com.example.challenge05.model.IMovieRepository
import com.example.challenge05.model.MovieDetailsResponse
import com.example.challenge05.model.MovieResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeRepo: IMovieRepository {
    override suspend fun getPopularMovies(page: Int): Flow<MovieResponse> {
        return flowOf()
    }

    override suspend fun getNowPlayingMovies(page: Int): Flow<MovieResponse> {
        return flowOf()
    }

    override suspend fun getUpcomingMovies(page: Int): Flow<MovieResponse> {
        return flowOf()
    }

    override suspend fun getMovieDetails(movieId: Int): Flow<MovieDetailsResponse> {
        return flowOf()
    }
}