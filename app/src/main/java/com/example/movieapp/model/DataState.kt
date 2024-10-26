package com.example.movieapp.model

sealed class AllMoviesState {
    object Loading : AllMoviesState()
    data class Success(val movies: List<Movie>) : AllMoviesState()
    data class Error(val message: String) : AllMoviesState()
}

sealed class MovieDetailsState {
    object Loading : MovieDetailsState()
    data class Success(val movie: MovieDetailsResponse) : MovieDetailsState()
    data class Error(val message: String) : MovieDetailsState()
}