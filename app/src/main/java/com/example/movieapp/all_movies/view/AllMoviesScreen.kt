package com.example.movieapp.all_movies.view

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator

import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movieapp.all_movies.components.MovieTabs
import com.example.movieapp.all_movies.view_model.AllMoviesViewModel
import com.example.movieapp.model.AllMoviesState
import com.example.movieapp.model.Movie
import com.example.movieapp.utils.Screens
import com.example.movieapp.utils.setMovieDetails
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