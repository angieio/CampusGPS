package com.tecsup.campusgps.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = CampusBlue,
    onPrimary = Color.White,
    primaryContainer = CampusBlueContainer,
    onPrimaryContainer = Color(0xFF07334F),

    secondary = CampusBlue,
    onSecondary = Color.White,

    background = CampusBackground,
    onBackground = CampusText,
    surface = CampusBackground,
    onSurface = CampusText,
    surfaceVariant = CampusCard,
    onSurfaceVariant = CampusTextSecondary,

    outline = CampusBlue,

    error = CampusError,
    onError = Color.White,
    errorContainer = CampusErrorContainer,
    onErrorContainer = CampusError
)

private val DarkColorScheme = darkColorScheme(
    primary = CampusBlueLight,
    onPrimary = Color(0xFF003351),
    primaryContainer = Color(0xFF004B73),
    onPrimaryContainer = CampusBlueContainer,

    secondary = CampusBlueLight,
    onSecondary = Color(0xFF003351),

    background = Color(0xFF111418),
    onBackground = Color(0xFFE1E2E8),
    surface = Color(0xFF111418),
    onSurface = Color(0xFFE1E2E8),
    surfaceVariant = Color(0xFF263440),
    onSurfaceVariant = Color(0xFFC1C7CE),

    outline = CampusBlueLight,

    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6)
)

@Composable
fun CampusGPSTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) {
            DarkColorScheme
        } else {
            LightColorScheme
        },
        typography = Typography,
        content = content
    )
}