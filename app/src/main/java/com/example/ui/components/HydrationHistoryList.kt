package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.model.HydrationEntry
import com.example.ui.theme.DarkNavyBorder
import com.example.ui.theme.DarkNavyCard
import com.example.ui.theme.DarkNavySurfaceVariant
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TurquoiseLight
import com.example.ui.theme.TurquoisePrimary

@Composable
fun HydrationHistoryList(
    history: List<HydrationEntry>,
    onRemoveEntry: (HydrationEntry) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(DarkNavyCard)
            .border(1.dp, DarkNavyBorder, RoundedCornerShape(20.dp))
            .padding(16.dp)
            .testTag("hydration_history_card")
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = null,
                    tint = TurquoisePrimary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Historique du jour",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }

            if (history.isNotEmpty()) {
                Text(
                    text = "${history.size} prises",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = TextSecondary,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        if (history.isEmpty()) {
            // Friendly empty state
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(DarkNavySurfaceVariant)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.WaterDrop,
                        contentDescription = null,
                        tint = TurquoiseLight.copy(alpha = 0.6f),
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Aucune gorgée enregistrée",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary
                    )
                )

                Text(
                    text = "Touchez « Ajouter 250 ml » pour commencer à vous hydrater !",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TextMuted
                    ),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        } else {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Show up to the last 6 entries
                history.take(6).forEach { entry ->
                    HydrationHistoryItem(
                        entry = entry,
                        onDelete = { onRemoveEntry(entry) }
                    )
                }

                if (history.size > 6) {
                    Text(
                        text = "+ ${history.size - 6} autres prises plus tôt aujourd'hui",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = TextMuted,
                            fontWeight = FontWeight.Medium
                        ),
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun HydrationHistoryItem(
    entry: HydrationEntry,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(DarkNavySurfaceVariant)
            .border(1.dp, DarkNavyBorder.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
            .padding(horizontal = 14.dp, vertical = 10.dp)
            .testTag("history_item_${entry.id}"),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(TurquoisePrimary.copy(alpha = 0.15f))
            ) {
                Icon(
                    imageVector = Icons.Default.WaterDrop,
                    contentDescription = null,
                    tint = TurquoisePrimary,
                    modifier = Modifier.size(18.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = "+ ${entry.amountMl} ml",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                )
                Text(
                    text = "À ${entry.formattedTime}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TextMuted
                    )
                )
            }
        }

        IconButton(
            onClick = onDelete,
            modifier = Modifier
                .size(32.dp)
                .testTag("delete_entry_${entry.id}")
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Supprimer la prise",
                tint = TextSecondary,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
