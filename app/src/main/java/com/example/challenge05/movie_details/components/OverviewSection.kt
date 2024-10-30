package com.example.challenge05.movie_details.components


import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun OverviewSection(overview: String?) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Text(text = "Overview", fontWeight = FontWeight.Bold)
        Text(text = overview ?: "No overview available")
    }
}
