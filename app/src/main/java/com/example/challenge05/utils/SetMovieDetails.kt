package com.example.challenge05.utils

import com.example.challenge05.model.Movie
import com.example.challenge05.model.MovieDetails

fun setMovieDetails(movie: Movie) {
    MovieDetails.title = movie.title
    MovieDetails.overview = movie.overview
    MovieDetails.release_date = movie.release_date
    MovieDetails.poster_path = movie.poster_path
    MovieDetails.vote_average = movie.vote_average
    MovieDetails.vote_count = movie.vote_count
    MovieDetails.id = movie.id
    MovieDetails.backdrop_path = movie.backdrop_path
    MovieDetails.original_language = movie.original_language
    MovieDetails.popularity = movie.popularity
    MovieDetails.video = movie.video
    MovieDetails.adult = movie.adult
    MovieDetails.original_title = movie.original_title
    MovieDetails.genre_ids = movie.genre_ids

}