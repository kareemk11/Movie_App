package com.example.movieapp.movie_details.components


import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AdultContentWarning(isAdult: Boolean) {
    if (isAdult) {
        Text(
            text = "Warning: Adult Content",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            color = Color.Red
        )
    }
}
