package com.example.challenge05.model

import com.example.challenge05.network.IMovieRemoteDataSource

class MovieRepository private constructor(private val remoteDataSource: IMovieRemoteDataSource) :
    IMovieRepository {

    companion object {
        @Volatile
        private var instance: MovieRepository? = null
        fun getInstance(remoteDataSource: IMovieRemoteDataSource): MovieRepository {
            return instance ?: synchronized(this) {
                instance ?: MovieRepository(remoteDataSource).also { instance = it }
            }
        }
    }

    override suspend fun getPopularMovies(page: Int) = remoteDataSource.getPopularMovies(page)
    override suspend fun getNowPlayingMovies(page: Int) = remoteDataSource.getNowPlayingMovies(page)
    override suspend fun getUpcomingMovies(page: Int) = remoteDataSource.getUpcomingMovies(page)
    override suspend fun getMovieDetails(movieId: Int) = remoteDataSource.getMovieDetails(movieId)
}