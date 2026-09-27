package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme =
    darkColorScheme(
        primary = TurquoisePrimary,
        onPrimary = Color(0xFF00373E),
        primaryContainer = Color(0xFF004F58),
        onPrimaryContainer = TurquoiseLight,
        secondary = TurquoiseTeal,
        onSecondary = Color(0xFF003831),
        secondaryContainer = Color(0xFF005147),
        onSecondaryContainer = AquaMint,
        tertiary = TurquoiseNeon,
        onTertiary = Color(0xFF00373D),
        background = DarkNavyBackground,
        onBackground = TextPrimary,
        surface = DarkNavySurface,
        onSurface = TextPrimary,
        surfaceVariant = DarkNavySurfaceVariant,
        onSurfaceVariant = TextSecondary,
        outline = DarkNavyBorder,
        outlineVariant = Color(0xFF1E2D40),
        error = AccentError,
        onError = Color.White
    )

@Composable
fun HydroPulseTheme(
    darkTheme: Boolean = true, // Default to dark theme as requested
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
