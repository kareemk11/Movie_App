package com.example.movieapp.all_movies

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

    // Track loading state for both tab and page changes
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

    // Check only the current tab's state
    val currentTabContent = when (selectedTab) {
        0 -> nowPlayingMovies
        1 -> popularMovies
        2 -> upcomingMovies
        else -> nowPlayingMovies
    }

    // Reset loading state when content is loaded
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

@OptIn(ExperimentalAnimationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MovieTabs(
    navController: NavController,
    popularMovies: AllMoviesState,
    nowPlayingMovies: AllMoviesState,
    upcomingMovies: AllMoviesState,
    viewModel: AllMoviesViewModel,
    selectedTab: Int,
    isLoading: Boolean,
    onTabSelected: (Int) -> Unit
) {
    val currentPage by viewModel.currentPage.collectAsState()
    val tabs = listOf("Now Playing", "Popular", "Upcoming")

    Column(modifier = Modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(),
            shadowElevation = 8.dp,
            tonalElevation = 2.dp
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Movies",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .animateContentSize()
                )

                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.primary,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                            height = 3.dp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        val isSelected = selectedTab == index
                        Tab(
                            selected = isSelected,
                            onClick = { onTabSelected(index) },
                            text = {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(vertical = 12.dp),
                                    color = if (isSelected)
                                        MaterialTheme.colorScheme.primary
                                    else
                                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                )
                            }
                        )
                    }
                }

                // Loading indicator below tabs
                AnimatedVisibility(
                    visible = isLoading,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        Box(modifier = Modifier.weight(1f)) {
            AnimatedContent(
                targetState = selectedTab,
                transitionSpec = {
                    fadeIn(animationSpec = tween(300)) with
                            fadeOut(animationSpec = tween(300))
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
            ) { targetTab ->
                Box(modifier = Modifier.fillMaxSize()) {
                    when (targetTab) {
                        0 -> if (nowPlayingMovies is AllMoviesState.Success) {
                            MovieList(
                                movies = nowPlayingMovies.movies,
                                onMovieClick = { movie -> navigateToMovieDetails(navController, movie) }
                            )
                        }
                        1 -> if (popularMovies is AllMoviesState.Success) {
                            MovieList(
                                movies = popularMovies.movies,
                                onMovieClick = { movie -> navigateToMovieDetails(navController, movie) }
                            )
                        }
                        2 -> if (upcomingMovies is AllMoviesState.Success) {
                            MovieList(
                                movies = upcomingMovies.movies,
                                onMovieClick = { movie -> navigateToMovieDetails(navController, movie) }
                            )
                        }
                    }

                    // Overlay loading indicator
                    this@Column.AnimatedVisibility(
                        visible = isLoading,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }

        // Pagination chips row with loading animation
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 2.dp
        ) {
            Box {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center,
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    items(5) { index ->
                        val page = index + 1
                        Surface(
                            onClick = {
                                if (!isLoading) {  // Prevent multiple clicks while loading
                                    viewModel.updatePage(page)
                                }
                            },
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .size(40.dp),
                            shape = CircleShape,
                            color = if (currentPage == page)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.surfaceVariant,
                            tonalElevation = if (currentPage == page) 4.dp else 0.dp
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = page.toString(),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = if (currentPage == page)
                                        MaterialTheme.colorScheme.onPrimary
                                    else
                                        MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontWeight = if (currentPage == page)
                                        FontWeight.Bold
                                    else
                                        FontWeight.Normal
                                )
                            }
                        }
                    }
                }

                // Loading overlay for pagination
                this@Column.AnimatedVisibility(
                    visible = isLoading,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f))
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MovieCard(
    movie: Movie,
    onClick: () -> Unit
) {
    var isHovered by remember { mutableStateOf(false) }

    Card(
        onClick = onClick,
        modifier = Modifier
            .width(150.dp)
            .height(320.dp)
            .graphicsLayer {
                scaleX = if (isHovered) 1.05f else 1f
                scaleY = if (isHovered) 1.05f else 1f
            }
            .pointerHoverIcon(PointerIcon.Hand)
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        when (event.type) {
                            PointerEventType.Enter -> isHovered = true
                            PointerEventType.Exit -> isHovered = false
                        }
                    }
                }
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            hoveredElevation = 8.dp
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(225.dp)
            ) {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500${movie.poster_path}",
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
                    contentScale = ContentScale.Crop
                )

                // Enhanced Rating Badge
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.95f),
                    shape = CircleShape,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.TopEnd)
                        .animateContentSize()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = String.format("%.1f", movie.vote_average),
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(12.dp)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = formatReleaseDate(movie.release_date),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MovieList(
    movies: List<Movie>,
    onMovieClick: (Movie) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = movies,
            key = { it.id }
        ) { movie ->
            MovieCard(
                movie = movie,
                onClick = { onMovieClick(movie) }
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
private fun formatReleaseDate(dateString: String): String {
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