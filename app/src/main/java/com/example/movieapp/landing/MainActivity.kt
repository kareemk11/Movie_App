package com.example.movieapp.landing

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.model.MovieRepository
import com.example.movieapp.movie_details.MovieDetailsScreen
import com.example.movieapp.movie_details.MovieDetailsViewModel
import com.example.movieapp.movie_details.MovieDetailsViewModelFactory
import com.example.movieapp.all_movies.AllMoviesScreen
import com.example.movieapp.all_movies.AllMoviesViewModel
import com.example.movieapp.all_movies.AllMoviesViewModelFactory
import com.example.movieapp.network.MovieRemoteDataSource
import com.example.movieapp.ui.theme.MovieAppTheme
import com.example.movieapp.utils.Screens

class MainActivity : ComponentActivity() {

    private val repository = MovieRepository.getInstance(MovieRemoteDataSource.getInstance())

    private val allMoviesViewModel by lazy {
        AllMoviesViewModelFactory(repository).create(AllMoviesViewModel::class.java)
    }
    private val movieDetailsViewModel by lazy {
        MovieDetailsViewModelFactory(repository).create(MovieDetailsViewModel::class.java)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieAppTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = Screens.AllMovies.route) {
                    composable(Screens.AllMovies.route) {
                        AllMoviesScreen(
                            viewModel = allMoviesViewModel,
                            navController = navController
                        )
                    }
                    composable(Screens.MovieDetails.route) {
                        MovieDetailsScreen(
                            viewModel = movieDetailsViewModel,
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}




