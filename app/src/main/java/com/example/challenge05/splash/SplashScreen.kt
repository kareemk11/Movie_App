package com.example.challenge05.splash

import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import com.airbnb.lottie.compose.*
import coil.compose.AsyncImage
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

@Composable
fun SplashScreen(
    onSplashComplete: () -> Unit
) {
    var isAnimationPlaying by remember { mutableStateOf(true) }
    var showContent by remember { mutableStateOf(false) }
    val scale = remember { Animatable(0.3f) }
    val rotationAngle = remember { Animatable(0f) }
    val shimmerEffect = rememberInfiniteTransition(label = "shimmer")

    val shimmerTranslateAnim = shimmerEffect.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer translate"
    )

    LaunchedEffect(key1 = true) {
        delay(300L)

        showContent = true

        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 800
            )
        )

        rotationAngle.animateTo(
            targetValue = 360f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = EaseOutQuart
            )
        )

        delay(2200L)
        onSplashComplete()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/original/628Dep6AxEtDxjZoGP78TsOxYbK.jpg",
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .blur(24.dp)
                .graphicsLayer {
                    translationX = shimmerTranslateAnim.value * -0.02f
                },
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.background.copy(alpha = 0.95f),
                            MaterialTheme.colorScheme.background.copy(alpha = 0.8f),
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                        )
                    )
                )
        )

        AnimatedVisibility(
            visible = showContent,
            enter = fadeIn(animationSpec = tween(1000)) +
                    slideInVertically(animationSpec = tween(1000)) { it / 2 }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(16.dp)
            ) {
                Surface(
                    modifier = Modifier
                        .size(220.dp)
                        .scale(scale.value)
                        .graphicsLayer {
                            rotationZ = rotationAngle.value
                        },
                    shape = CircleShape,
                    tonalElevation = 8.dp,
                    shadowElevation = 16.dp
                ) {
                    val composition by rememberLottieComposition(
                        LottieCompositionSpec.Url("https://assets4.lottiefiles.com/packages/lf20_CTaizi.json")
                    )
                    val progress by animateLottieCompositionAsState(
                        composition = composition,
                        isPlaying = isAnimationPlaying,
                        iterations = LottieConstants.IterateForever,
                        speed = 0.8f
                    )

                    LottieAnimation(
                        composition = composition,
                        progress = progress,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))

                Surface(
                    modifier = Modifier
                        .scale(scale.value)
                        .graphicsLayer {
                            alpha = 0.95f
                        },
                    shape = shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                    tonalElevation = 6.dp
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 40.dp, vertical = 20.dp)
                    ) {
                        Text(
                            buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                ) {
                                    append("Movie")
                                }
                                withStyle(
                                    style = SpanStyle(
                                        color = MaterialTheme.colorScheme.secondary,
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append("Pal")
                                }
                            },
                            style = MaterialTheme.typography.displayMedium.copy(
                                letterSpacing = 3.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Your Personal Movie Companion",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                letterSpacing = 1.sp
                            ),
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }
    }
}