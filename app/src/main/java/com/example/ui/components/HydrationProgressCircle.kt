package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DarkNavyBorder
import com.example.ui.theme.DarkNavySurface
import com.example.ui.theme.DarkNavySurfaceVariant
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TurquoiseLight
import com.example.ui.theme.TurquoiseNeon
import com.example.ui.theme.TurquoisePrimary
import com.example.ui.theme.TurquoiseTeal
import java.text.NumberFormat
import java.util.Locale

@Composable
fun HydrationProgressCircle(
    currentIntakeMl: Int,
    goalMl: Int,
    modifier: Modifier = Modifier
) {
    val progress = if (goalMl > 0) (currentIntakeMl.toFloat() / goalMl.toFloat()).coerceIn(0f, 1f) else 0f

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 650, easing = FastOutSlowInEasing),
        label = "HydrationProgressAnimation"
    )

    // Breathing pulse animation for the glow effect
    val infiniteTransition = rememberInfiniteTransition(label = "GlowTransition")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.25f,
        targetValue = 0.55f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "PulseAlpha"
    )

    val scaleAnim = remember { Animatable(1f) }
    LaunchedEffect(currentIntakeMl) {
        if (currentIntakeMl > 0) {
            scaleAnim.animateTo(1.04f, animationSpec = tween(120))
            scaleAnim.animateTo(1f, animationSpec = tween(180))
        }
    }

    val percentage = (progress * 100).toInt()
    val formattedIntake = NumberFormat.getNumberInstance(Locale.FRANCE).format(currentIntakeMl)
    val formattedGoal = NumberFormat.getNumberInstance(Locale.FRANCE).format(goalMl)

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(270.dp)
            .scale(scaleAnim.value)
            .testTag("hydration_progress_circle")
    ) {
        // Glowing halo canvas
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val strokeWidth = 18.dp.toPx()
            val radius = (size.minDimension - strokeWidth) / 2f

            // Ambient background glow
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        TurquoisePrimary.copy(alpha = 0.12f * (progress + 0.2f).coerceAtMost(1f) * (pulseAlpha / 0.4f)),
                        Color.Transparent
                    ),
                    center = center,
                    radius = radius * 1.35f
                ),
                radius = radius * 1.35f,
                center = center
            )

            // Outer subtle track rim
            drawCircle(
                color = DarkNavySurfaceVariant,
                radius = radius,
                center = center,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            // Inner track border for depth
            drawCircle(
                color = DarkNavyBorder.copy(alpha = 0.6f),
                radius = radius + (strokeWidth / 2f),
                center = center,
                style = Stroke(width = 1.dp.toPx())
            )
            drawCircle(
                color = DarkNavyBorder.copy(alpha = 0.6f),
                radius = radius - (strokeWidth / 2f),
                center = center,
                style = Stroke(width = 1.dp.toPx())
            )

            // Progress Arc with vibrant turquoise gradient
            if (animatedProgress > 0f) {
                val sweepAngle = 360f * animatedProgress
                drawArc(
                    brush = Brush.sweepGradient(
                        colors = listOf(
                            TurquoiseTeal,
                            TurquoisePrimary,
                            TurquoiseNeon,
                            TurquoiseLight,
                            TurquoiseTeal
                        ),
                        center = center
                    ),
                    startAngle = -90f,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    topLeft = Offset(center.x - radius, center.y - radius),
                    size = Size(radius * 2f, radius * 2f),
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                )
            }
        }

        // Center Content
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {
            // Water Drop with subtle turquoise glow badge
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(DarkNavySurface)
            ) {
                Icon(
                    imageVector = Icons.Default.WaterDrop,
                    contentDescription = "Goutte d'eau",
                    tint = TurquoisePrimary,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Current Water Amount (Bold hero text)
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = formattedIntake,
                    style = MaterialTheme.typography.displayMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        letterSpacing = (-1).sp
                    ),
                    modifier = Modifier.testTag("current_intake_value")
                )
                Text(
                    text = " ml",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = TurquoiseLight
                    ),
                    modifier = Modifier.padding(bottom = 6.dp, start = 2.dp)
                )
            }

            // Target Goal subtitle
            Text(
                text = "sur $formattedGoal ml (2L)",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = TextSecondary,
                    fontWeight = FontWeight.Medium
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Percentage pill chip
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        if (currentIntakeMl >= goalMl) TurquoisePrimary else DarkNavyBorder
                    )
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(
                    text = if (currentIntakeMl >= goalMl) "100 % (Atteint !)" else "$percentage %",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = if (currentIntakeMl >= goalMl) Color(0xFF00373E) else TurquoisePrimary
                    ),
                    modifier = Modifier.testTag("progress_percent_label")
                )
            }
        }
    }
}
