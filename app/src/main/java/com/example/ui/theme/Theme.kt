package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val SocColorScheme = darkColorScheme(
    primary = SocCyan,
    onPrimary = Color.Black,
    primaryContainer = SocCardBorder,
    onPrimaryContainer = SocCyan,
    secondary = SocEmerald,
    onSecondary = Color.Black,
    secondaryContainer = SocEmeraldDim,
    onSecondaryContainer = Color(0xFFD1FAE5),
    tertiary = SocAmber,
    onTertiary = Color.Black,
    tertiaryContainer = SocAmberDim,
    onTertiaryContainer = Color(0xFFFEF3C7),
    error = SocRed,
    onError = Color.White,
    errorContainer = SocRedDim,
    onErrorContainer = Color(0xFFFEE2E2),
    background = SocBackground,
    onBackground = SocTextPrimary,
    surface = SocSurface,
    onSurface = SocTextPrimary,
    surfaceVariant = SocSurfaceVariant,
    onSurfaceVariant = SocTextSecondary,
    outline = SocCardBorder,
    outlineVariant = SocCardBorderHighlight
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    // We enforce dark SOC theme for threat hunting console aesthetic
    MaterialTheme(
        colorScheme = SocColorScheme,
        typography = Typography,
        content = content
    )
}
