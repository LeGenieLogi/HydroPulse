package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.SportsGymnastics
import androidx.compose.material.icons.filled.WaterDamage
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.Undo
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AccentError
import com.example.ui.theme.CyanGlow
import com.example.ui.theme.DarkNavyBorder
import com.example.ui.theme.DarkNavyCard
import com.example.ui.theme.DarkNavySurfaceVariant
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TurquoiseLight
import com.example.ui.theme.TurquoiseNeon
import com.example.ui.theme.TurquoisePrimary
import com.example.ui.theme.TurquoiseTeal

@Composable
fun HydrationActionButtons(
    onAdd250ml: () -> Unit,
    onAdd100ml: () -> Unit,
    onAdd500ml: () -> Unit,
    onCustomAdd: () -> Unit,
    onReset: () -> Unit,
    onUndo: () -> Unit,
    canUndo: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Hero button: +250 ml (Grand verre d'eau standard)
        Button(
            onClick = onAdd250ml,
            shape = RoundedCornerShape(22.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = TurquoisePrimary,
                contentColor = Color(0xFF00373E)
            ),
            contentPadding = PaddingValues(vertical = 18.dp, horizontal = 24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .shadow(
                    elevation = 12.dp,
                    shape = RoundedCornerShape(22.dp),
                    spotColor = TurquoisePrimary,
                    ambientColor = TurquoiseNeon
                )
                .testTag("add_250ml_button")
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    imageVector = Icons.Default.LocalDrink,
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Ajouter 250 ml",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 0.5.sp
                    )
                )
            }
        }

        // Secondary quick increment buttons: +100ml, +500ml, +Personnalisé
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // +100 ml
            OutlinedButton(
                onClick = onAdd100ml,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = TurquoiseLight,
                    containerColor = DarkNavyCard
                ),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = Brush.horizontalGradient(listOf(DarkNavyBorder, TurquoiseTeal.copy(alpha = 0.5f)))
                ),
                contentPadding = PaddingValues(vertical = 12.dp, horizontal = 12.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp)
                    .testTag("add_100ml_button")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = TurquoiseLight
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "100 ml",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            // +500 ml
            OutlinedButton(
                onClick = onAdd500ml,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = TurquoiseNeon,
                    containerColor = DarkNavyCard
                ),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = Brush.horizontalGradient(listOf(TurquoisePrimary.copy(alpha = 0.6f), DarkNavyBorder))
                ),
                contentPadding = PaddingValues(vertical = 12.dp, horizontal = 12.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp)
                    .testTag("add_500ml_button")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = TurquoiseNeon
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "500 ml",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            // + Autre
            OutlinedButton(
                onClick = onCustomAdd,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = TextSecondary,
                    containerColor = DarkNavyCard
                ),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = Brush.linearGradient(listOf(DarkNavyBorder, DarkNavyBorder))
                ),
                contentPadding = PaddingValues(vertical = 12.dp, horizontal = 10.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp)
                    .testTag("add_custom_button")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.WaterDrop,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = TextSecondary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Autre",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }

        // Action Row: Reset and Undo
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Bouton de Reset
            Button(
                onClick = onReset,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkNavyCard,
                    contentColor = AccentError
                ),
                contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .border(1.dp, AccentError.copy(alpha = 0.35f), RoundedCornerShape(16.dp))
                    .testTag("reset_button")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Refresh,
                        contentDescription = "Réinitialiser",
                        tint = AccentError,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Réinitialiser",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = AccentError
                        )
                    )
                }
            }

            // Bouton d'annulation (Undo) si au moins 1 entrée existe
            if (canUndo) {
                OutlinedButton(
                    onClick = onUndo,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = DarkNavyCard,
                        contentColor = TextSecondary
                    ),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        brush = Brush.linearGradient(listOf(DarkNavyBorder, DarkNavyBorder))
                    ),
                    contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("undo_button")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Undo,
                            contentDescription = "Annuler",
                            tint = TextSecondary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Annuler",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = TextSecondary
                            )
                        )
                    }
                }
            }
        }
    }
}
