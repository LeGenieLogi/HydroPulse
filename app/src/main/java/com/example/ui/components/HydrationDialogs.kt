package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.ui.theme.AccentError
import com.example.ui.theme.DarkNavyBorder
import com.example.ui.theme.DarkNavyCard
import com.example.ui.theme.DarkNavySurface
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TurquoiseLight
import com.example.ui.theme.TurquoisePrimary

@Composable
fun ResetConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = DarkNavySurface,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.WarningAmber,
                    contentDescription = null,
                    tint = AccentError,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = "Réinitialiser le suivi ?",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                )
            }
        },
        text = {
            Text(
                text = "Toutes les gorgées d'aujourd'hui seront remises à zéro (0 ml sur 2 000 ml). Cette action ne peut pas être annulée.",
                style = MaterialTheme.typography.bodyMedium.copy(color = TextSecondary)
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = AccentError,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.testTag("confirm_reset_button")
            ) {
                Text("Oui, réinitialiser", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.testTag("cancel_reset_button")
            ) {
                Text("Annuler", color = TextSecondary)
            }
        }
    )
}

@Composable
fun CustomAmountDialog(
    onAddAmount: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    var textValue by remember { mutableStateOf("330") }
    val presets = listOf(150, 200, 330, 400, 750)

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = DarkNavySurface,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.WaterDrop,
                    contentDescription = null,
                    tint = TurquoisePrimary,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = "Quantité personnalisée",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                )
            }
        },
        text = {
            Column {
                Text(
                    text = "Choisissez un format rapide ou entrez les millilitres consommés :",
                    style = MaterialTheme.typography.bodyMedium.copy(color = TextSecondary)
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Presets row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    presets.forEach { preset ->
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(
                                    if (textValue == preset.toString()) TurquoisePrimary else DarkNavyCard
                                )
                                .clickable { textValue = preset.toString() }
                                .padding(vertical = 8.dp)
                        ) {
                            Text(
                                text = "${preset}",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (textValue == preset.toString()) Color(0xFF00373E) else TurquoiseLight
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = textValue,
                    onValueChange = { input ->
                        if (input.all { it.isDigit() } && input.length <= 5) {
                            textValue = input
                        }
                    },
                    label = { Text("Volume (ml)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = TurquoisePrimary,
                        unfocusedBorderColor = DarkNavyBorder,
                        focusedLabelColor = TurquoisePrimary,
                        unfocusedLabelColor = TextSecondary,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("custom_amount_input")
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val amount = textValue.toIntOrNull() ?: 250
                    if (amount > 0) {
                        onAddAmount(amount)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = TurquoisePrimary,
                    contentColor = Color(0xFF00373E)
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.testTag("confirm_custom_amount_button")
            ) {
                Text("Ajouter", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.testTag("cancel_custom_amount_button")
            ) {
                Text("Annuler", color = TextSecondary)
            }
        }
    )
}
