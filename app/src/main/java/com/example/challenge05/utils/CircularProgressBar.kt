package com.example.challenge05.utils

import androidx.compose.runtime.Composable


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp



@Composable
fun CircularProgressBar() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
        Text(text = "Loading...", modifier = Modifier.padding(top = 100.dp))
    }
}

