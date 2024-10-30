package com.example.challenge05.all_movies.view

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.challenge05.all_movies.components.MovieTabs
import com.example.challenge05.all_movies.view_model.AllMoviesViewModel
import com.example.challenge05.model.AllMoviesState
import com.example.challenge05.model.Movie
import com.example.challenge05.utils.Screens
import com.example.challenge05.utils.setMovieDetails
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AllMoviesScreen(
    navController: NavController,
    viewModel: AllMoviesViewModel
) {
    val selectedTab by viewModel.selectedTab.collectAsState()
    val currentPage by viewModel.currentPage.collectAsState()
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(selectedTab, currentPage) {
        isLoading = true
        when (selectedTab) {
            0 -> viewModel.getNowPlayingMovies(page = currentPage)
            1 -> viewModel.getPopularMovies(page = currentPage)
            2 -> viewModel.getUpcomingMovies(page = currentPage)
        }
    }

    val popularMovies by viewModel.popularMovies.collectAsState()
    val nowPlayingMovies by viewModel.nowPlayingMovies.collectAsState()
    val upcomingMovies by viewModel.upcomingMovies.collectAsState()

    val currentTabContent = when (selectedTab) {
        0 -> nowPlayingMovies
        1 -> popularMovies
        2 -> upcomingMovies
        else -> nowPlayingMovies
    }

    LaunchedEffect(currentTabContent) {
        if (currentTabContent is AllMoviesState.Success) {
            isLoading = false
        }
    }

    MovieTabs(
        navController = navController,
        popularMovies = popularMovies,
        nowPlayingMovies = nowPlayingMovies,
        upcomingMovies = upcomingMovies,
        viewModel = viewModel,
        selectedTab = selectedTab,
        isLoading = isLoading,
        onTabSelected = { tab -> viewModel.updateSelectedTab(tab) }
    )

    if (currentTabContent is AllMoviesState.Error) {
        Toast.makeText(
            LocalContext.current,
            (currentTabContent).message,
            Toast.LENGTH_SHORT
        ).show()
    }
}



@RequiresApi(Build.VERSION_CODES.O)
 fun formatReleaseDate(dateString: String): String {
    return try {
        val date = LocalDate.parse(dateString)
        date.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
    } catch (e: Exception) {
        dateString
    }
}

fun navigateToMovieDetails(navController: NavController, movie: Movie) {
    setMovieDetails(movie)
    navController.navigate(Screens.MovieDetails.route)
}