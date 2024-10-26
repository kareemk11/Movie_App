package com.example.movieapp.utils

sealed class Screens (val route: String){
    object AllMovies: Screens("all_movies")
    object MovieDetails: Screens("movie_details")
}