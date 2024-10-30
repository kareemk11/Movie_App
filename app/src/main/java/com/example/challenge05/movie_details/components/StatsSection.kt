package com.example.challenge05.movie_details.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.challenge05.model.MovieDetailsResponse

@Composable
fun StatsSection(movieDetails: MovieDetailsResponse) {
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
