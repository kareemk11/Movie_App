package com.example.movieapp.movie_details.components

import android.os.Build


import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.movieapp.model.MovieDetailsResponse
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
 fun QuickInfoSection(movieDetails: MovieDetailsResponse) {
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
