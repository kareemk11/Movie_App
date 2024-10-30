package com.example.challenge05.all_movies.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign


@Composable
fun AnimatedAppTitle(
    modifier: Modifier = Modifier
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = 800,
                easing = EaseOutQuart
            )
        ) + expandVertically(
            animationSpec = tween(
                durationMillis = 600,
                easing = EaseOutQuart
            )
        )
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.5.sp
                    )
                ) {
                    append("Movie")
                }
                append("")
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.9f),
                        fontWeight = FontWeight.Normal,
                        letterSpacing = 0.5.sp
                    )
                ) {
                    append("Pal")
                }
            },
            modifier = modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                ),
            style = MaterialTheme.typography.headlineMedium.copy(
                textAlign = TextAlign.Center
            )
        )
    }
}

