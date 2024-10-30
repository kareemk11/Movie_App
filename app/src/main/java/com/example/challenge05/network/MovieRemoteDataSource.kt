package com.example.challenge05.network

import com.example.challenge05.model.MovieDetailsResponse
import com.example.challenge05.model.MovieResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitHelper {

    private const val BASE_URL: String = "https://api.themoviedb.org/3/movie/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}

class MovieRemoteDataSource private constructor() : IMovieRemoteDataSource {
    private val apiService: MovieApiService =
        RetrofitHelper.getInstance().create(MovieApiService::class.java)

    companion object {
        @Volatile
        private var instance: MovieRemoteDataSource? = null
        fun getInstance(): MovieRemoteDataSource {
            return instance ?: synchronized(this) {
                instance ?: MovieRemoteDataSource().also { instance = it }
            }
        }
    }

    override suspend fun getPopularMovies(page: Int): Flow<MovieResponse> {
        return flowOf(apiService.getPopularMovies(page = page))
    }

    override suspend fun getNowPlayingMovies(page: Int): Flow<MovieResponse> {
        return flowOf(apiService.getNowPlayingMovies(page = page))
    }

    override suspend fun getUpcomingMovies(page: Int): Flow<MovieResponse> {
        return flowOf(apiService.getUpcomingMovies(page = page))
    }

    override suspend fun getMovieDetails(movieId: Int): Flow<MovieDetailsResponse> {
        return flowOf(apiService.getMovieDetails(movieId = movieId))
    }


}