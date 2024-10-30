package com.example.challenge05.network

import com.example.challenge05.model.MovieDetailsResponse
import com.example.challenge05.model.MovieResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeRemoteDataSource(
    private val movieResponseArr: List<MovieResponse>,
    private val movieDetailsResponseArr: List<MovieDetailsResponse>
) : IMovieRemoteDataSource {
    override suspend fun getPopularMovies(page: Int): Flow<MovieResponse> {
        return if (movieResponseArr.isNotEmpty() && page > 0 && page <= movieResponseArr.size) {
            flowOf(movieResponseArr[page - 1])
        } else {
            flowOf()
        }
    }

    override suspend fun getNowPlayingMovies(page: Int): Flow<MovieResponse> {
        return if (movieResponseArr.isNotEmpty() && page > 0 && page <= movieResponseArr.size) {
            flowOf(movieResponseArr[page - 1])
        } else {
            flowOf()
        }
    }

    override suspend fun getUpcomingMovies(page: Int): Flow<MovieResponse> {
        return if (movieResponseArr.isNotEmpty() && page > 0 && page <= movieResponseArr.size) {
            flowOf(movieResponseArr[page - 1])
        } else {
            flowOf()
        }
    }

    override suspend fun getMovieDetails(movieId: Int): Flow<MovieDetailsResponse> {
        return if (movieDetailsResponseArr.isNotEmpty() && movieId >= 0 && movieId < movieDetailsResponseArr.size) {
            flowOf(movieDetailsResponseArr[movieId])
        } else {
            flowOf()
        }
    }



}