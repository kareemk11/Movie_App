package com.example.movieapp.movie_details.components


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.movieapp.model.MovieDetailsResponse

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MovieDetailsContent(
    details: MovieDetailsResponse,
    onBackPressed: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            BackdropSection(details.backdropPath, onBackPressed)
            MovieInfoSection(details)
        }
    }
}
