package com.example.challenge05.network

import com.example.challenge05.model.MovieResponse
import com.example.challenge05.conestants.Keys
import com.example.challenge05.model.MovieDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieApiService {
    @GET("popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = Keys.MOVIE_API_KEY,
        @Query("page") page: Int
    ): MovieResponse

    @GET("now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key") apiKey: String = Keys.MOVIE_API_KEY,
        @Query("page") page: Int
    ): MovieResponse

    @GET("upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String = Keys.MOVIE_API_KEY,
        @Query("page") page: Int
    ): MovieResponse

    @GET("{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = Keys.MOVIE_API_KEY
    ): MovieDetailsResponse

}