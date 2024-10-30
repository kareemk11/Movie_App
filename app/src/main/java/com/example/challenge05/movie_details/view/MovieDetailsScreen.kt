package com.example.challenge05.movie_details.view

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.challenge05.model.MovieDetails
import com.example.challenge05.model.MovieDetailsState
import com.example.challenge05.movie_details.components.LoadingOverlay
import com.example.challenge05.movie_details.components.MovieDetailsContent
import com.example.challenge05.movie_details.view_model.MovieDetailsViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MovieDetailsScreen(
    navController: NavController,
    viewModel: MovieDetailsViewModel
) {
    val movieDetails by viewModel.movieDetails.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getMovieDetails(MovieDetails.id)
    }

    when (movieDetails) {
        is MovieDetailsState.Loading -> LoadingOverlay()
        is MovieDetailsState.Success -> {
            val details = (movieDetails as MovieDetailsState.Success).movie
            MovieDetailsContent(details = details) {
                navController.popBackStack()
            }
        }
        is MovieDetailsState.Error -> {
            Toast.makeText(
                LocalContext.current,
                (movieDetails as MovieDetailsState.Error).message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}













