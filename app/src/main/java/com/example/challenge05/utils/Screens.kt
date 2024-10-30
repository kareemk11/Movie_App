package com.example.challenge05.utils

sealed class Screens (val route: String){
    object AllMovies: Screens("all_movies")
    object MovieDetails: Screens("movie_details")
    object Splash: Screens("splash")
    object NoInternet: Screens("no_internet")

    fun movieDetailsRoute(movieId: Int) = "$MovieDetails/$movieId"
}