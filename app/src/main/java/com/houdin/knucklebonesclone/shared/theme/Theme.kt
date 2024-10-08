package com.houdin.knucklebonesclone.shared.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkOnSurface,
    secondary = DarkSecondary,
    onSecondary = DarkOnSurface,
    tertiary = DarkTertiary,
    background = DarkBackground,
    surface = DarkSurface,
    onSurface = DarkOnSurface
)

private val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = LightOnSurface,
    secondary = LightSecondary,
    onSecondary = LightOnSurface,
    tertiary = LightTertiary,
    background = LightBackground,
    surface = LightSurface,
    onSurface = LightOnSurface
)

@Composable
fun KnucklebonesCloneTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}