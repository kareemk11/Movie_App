package com.example.movieapp.movie_details

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movieapp.model.Genre
import com.example.movieapp.model.MovieDetails
import com.example.movieapp.model.MovieDetailsResponse
import com.example.movieapp.model.MovieDetailsState
import com.example.movieapp.model.ProductionCompany
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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

    if (movieDetails is MovieDetailsState.Loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (movieDetails is MovieDetailsState.Success) {
        val details = (movieDetails as MovieDetailsState.Success).movie
        MovieDetailsContent(details)
    } else if (movieDetails is MovieDetailsState.Error) {
        Toast.makeText(
            LocalContext.current,
            (movieDetails as MovieDetailsState.Error).message,
            Toast.LENGTH_SHORT
        ).show()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun MovieDetailsContent(movieDetails: MovieDetailsResponse) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Backdrop Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                AsyncImage(
                    model = movieDetails.backdropPath?.let { "https://image.tmdb.org/t/p/w1280$it" },
                    contentDescription = "Movie Backdrop",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Gradient Scrim
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    MaterialTheme.colorScheme.background
                                )
                            )
                        )
                )
            }

            // Content
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                // Title and Rating Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = movieDetails.title,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        if (!movieDetails.tagline.isNullOrEmpty()) {
                            Text(
                                text = movieDetails.tagline,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontStyle = FontStyle.Italic
                            )
                        }
                    }

                    RatingBadge(
                        rating = movieDetails.voteAverage,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Quick Info Row
                QuickInfoSection(movieDetails)

                Spacer(modifier = Modifier.height(16.dp))

                // Stats Cards
                StatsSection(movieDetails)

                Spacer(modifier = Modifier.height(16.dp))

                // Genres
                if (movieDetails.genres.isNotEmpty()) {
                    GenresSection(movieDetails.genres)
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Overview
                Text(
                    text = "Overview",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = movieDetails.overview,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Production Companies
                if (movieDetails.productionCompanies.isNotEmpty()) {
                    ProductionCompaniesSection(movieDetails.productionCompanies)
                }

                // Adult Content Warning
                if (movieDetails.adult) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Text(
                            text = "Adult Content",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun QuickInfoSection(movieDetails: MovieDetailsResponse) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val formattedDate = try {
            LocalDate.parse(movieDetails.releaseDate)
                .format(DateTimeFormatter.ofPattern("MMMM d, yyyy"))
        } catch (e: Exception) {
            movieDetails.releaseDate
        }

        Text(
            text = "📅 $formattedDate",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "🎬 ${movieDetails.originalLanguage.uppercase()}",
            style = MaterialTheme.typography.bodyMedium
        )
        if (movieDetails.runtime > 0) {
            Text(
                text = "⏱️ ${movieDetails.runtime}min",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun StatsSection(movieDetails: MovieDetailsResponse) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        StatCard(
            title = "Popularity",
            value = String.format("%.1f", movieDetails.popularity),
            modifier = Modifier.weight(1f)
        )
        StatCard(
            title = "Votes",
            value = movieDetails.voteCount.toString(),
            modifier = Modifier.weight(1f)
        )
        if (movieDetails.budget > 0) {
            StatCard(
                title = "Budget",
                value = "$${movieDetails.budget / 1_000_000}M",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun GenresSection(genres: List<Genre>) {
    Text(
        text = "Genres",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(8.dp))
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        genres.forEach { genre ->
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Text(
                    text = genre.name,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}

@Composable
private fun ProductionCompaniesSection(companies: List<ProductionCompany>) {
    Text(
        text = "Production Companies",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(8.dp))
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        companies.forEach { company ->
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (company.logoPath != null) {
                        AsyncImage(
                            model = "https://image.tmdb.org/t/p/w92${company.logoPath}",
                            contentDescription = "Company logo",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(MaterialTheme.shapes.small),
                            contentScale = ContentScale.Fit
                        )
                    }
                    Column {
                        Text(
                            text = company.name,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = company.originCountry,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RatingBadge(rating: Double, modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "★",
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = String.format("%.1f", rating),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
private fun StatCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}