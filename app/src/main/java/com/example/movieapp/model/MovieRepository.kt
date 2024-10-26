package com.example.movieapp.model

import com.example.movieapp.network.IMovieRemoteDataSource
import com.example.movieapp.network.MovieRemoteDataSource

class MovieRepository private constructor(private val remoteDataSource: IMovieRemoteDataSource) {

    companion object {
        @Volatile
        private var instance: MovieRepository? = null
        fun getInstance(remoteDataSource: MovieRemoteDataSource): MovieRepository {
            return instance ?: synchronized(this) {
                instance ?: MovieRepository(remoteDataSource).also { instance = it }
            }
        }
    }

    suspend fun getPopularMovies(page: Int) = remoteDataSource.getPopularMovies(page)
    suspend fun getNowPlayingMovies(page: Int) = remoteDataSource.getNowPlayingMovies(page)
    suspend fun getUpcomingMovies(page: Int) = remoteDataSource.getUpcomingMovies(page)
    suspend fun getMovieDetails(movieId: Int) = remoteDataSource.getMovieDetails(movieId)
}