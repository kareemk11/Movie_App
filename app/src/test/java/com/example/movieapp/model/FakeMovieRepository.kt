package com.example.movieapp.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeMovieRepository(
    private val movieResponseArr: List<MovieResponse>,
    private val movieDetailsResponseArr: List<MovieDetailsResponse>
) : IMovieRepository {
    override suspend fun getPopularMovies(page: Int): Flow<MovieResponse> {
        return if (movieResponseArr.isNotEmpty() && page > 0 && page <= movieResponseArr.size) {
            flowOf(movieResponseArr[page - 1])
        } else {
            throw Exception("No movies available")
        }
    }

    override suspend fun getNowPlayingMovies(page: Int): Flow<MovieResponse> {
        return if (movieResponseArr.isNotEmpty() && page in 1..movieResponseArr.size) {
            flowOf(movieResponseArr[page - 1])
        } else {
            throw Exception("No movies available")
        }
    }


    override suspend fun getUpcomingMovies(page: Int): Flow<MovieResponse> {
        return if (movieResponseArr.isNotEmpty() && page > 0 && page <= movieResponseArr.size) {
            flowOf(movieResponseArr[page - 1])
        } else {
            throw Exception("No movies available")
        }
    }

    override suspend fun getMovieDetails(movieId: Int): Flow<MovieDetailsResponse> {
        return if (movieDetailsResponseArr.isNotEmpty() && movieId >= 0 && movieId < movieDetailsResponseArr.size) {
            flowOf(movieDetailsResponseArr[movieId])
        } else {
            throw Exception("No movie details available")
        }
    }
}