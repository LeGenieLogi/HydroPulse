package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.theme.DarkNavyCard
import com.example.ui.theme.DarkNavyBorder
import com.example.ui.theme.DarkNavySurfaceVariant
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TurquoiseNeon
import com.example.ui.theme.TurquoisePrimary

@Composable
fun GlassesVisualizer(
    currentIntakeMl: Int,
    onAddGlass: () -> Unit,
    modifier: Modifier = Modifier
) {
    val totalGlasses = 8
    val filledGlasses = (currentIntakeMl / 250).coerceIn(0, totalGlasses)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(DarkNavyCard)
            .border(1.dp, DarkNavyBorder, RoundedCornerShape(20.dp))
            .padding(16.dp)
            .testTag("glasses_visualizer_card")
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Verres du jour",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )

                Text(
                    text = "$filledGlasses / $totalGlasses (250 ml)",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = if (filledGlasses >= totalGlasses) TurquoiseNeon else TextSecondary
                    ),
                    modifier = Modifier.testTag("glasses_counter_text")
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 8 glasses arranged in a row (or 2 rows of 4)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (index in 0 until totalGlasses) {
                    val isFilled = index < filledGlasses
                    val glassBg by animateColorAsState(
                        targetValue = if (isFilled) TurquoisePrimary else DarkNavySurfaceVariant,
                        animationSpec = tween(durationMillis = 300),
                        label = "GlassBgAnim"
                    )
                    val iconTint by animateColorAsState(
                        targetValue = if (isFilled) Color(0xFF00373E) else DarkNavyBorder,
                        animationSpec = tween(durationMillis = 300),
                        label = "GlassIconAnim"
                    )

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(glassBg)
                            .border(
                                width = 1.dp,
                                color = if (isFilled) TurquoiseNeon else DarkNavyBorder,
                                shape = CircleShape
                            )
                            .clickable(onClick = onAddGlass)
                            .testTag("glass_item_$index")
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalDrink,
                            contentDescription = "Verre ${index + 1}",
                            tint = iconTint,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}
