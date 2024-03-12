package com.example.testcicd.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val lightColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = LightOnPrimary,
    primaryContainer = LightPrimaryContainer,
    onPrimaryContainer = LightOnPrimaryContainer,
    secondary = LightSecondary,
    onSecondary = LightOnSecondary,
    secondaryContainer = LightSecondaryContainer,
    onSecondaryContainer = LightOnSecondaryContainer,
    background = LightBackground,
    onBackground  = LightOnBackground,
    surface = LightSurface,
    onSurface = LightOnSurface,
    error = LightError,
    onError = LightOnError,
    errorContainer = LightErrorContainer,
    onErrorContainer = LightOnErrorContainer,
    outline = LightOutline,
    outlineVariant = LightOutlineVariant
)

private val extendedLightColorScheme = AppColors(
    material = lightColorScheme,

    shadow = LightShadow,

    surfaceForceLight = LightSurface,
    surface1 = LightSurface1,
    surface2 = LightSurface2,
    surface3 = LightSurface3,
    surface4 = LightSurface4,
    surface5 = LightSurface5,
    surface6 = LightSurface6,
    surface7 = LightSurface7,
    surface8 = LightSurface8,

    specialSurface = LightSpecialSurface,
    specialSurface2 = LightSpecialSurface2,
    specialSurface3 = LightSpecialSurface3,
    specialSurface4 = LightSpecialSurface4,
    specialSurface5 = LightSpecialSurface5,
    specialSurface6 = LightSpecialSurface6,
    specialSurface7 = LightSpecialSurface7,
    specialSurface8 = LightSpecialSurface8,
    specialSurface9 = LightSpecialSurface9,
    specialSurface10 = LightSpecialSurface10,
    specialSurface11 = LightSpecialSurface11,
    specialSurface12 = LightSpecialSurface12,
    specialSurface13 = LightSpecialSurface13,
    specialSurface14 = LightSpecialSurface14,
    specialSurface15 = LightSpecialSurface15,
    specialSurface16 = LightSpecialSurface16,
    specialGrey = LightSpecialGrey,

    textPrimary = LightTextPrimary,
    textPrimaryForceLight = LightTextPrimary,
    textSecondary = LightTextSecondary,

    pricePrimary = LightPricePrimary,
    priceSecondary = LightPriceSecondary,
    priceSurfacePrimary = LightPriceSurfacePrimary,
    priceSurfaceSecondary = LightPriceSurfaceSecondary,

    syncBarColor1 = SyncBarColor1,
    syncBarColor2 = SyncBarColor2,
    syncBarColor3 = SyncBarColor3,
    syncBarColor4 = SyncBarColor4,
    syncBarColor5 = SyncBarColor5
)

private val darkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkOnPrimary,
    primaryContainer = DarkPrimaryContainer,
    onPrimaryContainer = DarkOnPrimaryContainer,
    secondary = DarkSecondary,
    onSecondary = DarkOnSecondary,
    secondaryContainer = DarkSecondaryContainer,
    onSecondaryContainer = DarkOnSecondaryContainer,
    background = DarkBackground,
    onBackground  = DarkOnBackground,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    error = DarkError,
    onError = DarkOnError,
    errorContainer = DarkErrorContainer,
    onErrorContainer = DarkOnErrorContainer,
    outline = DarkOutline,
    outlineVariant = DarkOutlineVariant
)

private val extendedDarkColorScheme = AppColors(
    material = darkColorScheme,

    shadow = DarkShadow,

    surfaceForceLight = LightSurface,
    surface1 = DarkSurface1,
    surface2 = DarkSurface2,
    surface3 = DarkSurface3,
    surface4 = DarkSurface4,
    surface5 = DarkSurface5,
    surface6 = DarkSurface6,
    surface7 = DarkSurface7,
    surface8 = DarkSurface8,

    specialSurface = DarkSpecialSurface,
    specialSurface2 = DarkSpecialSurface2,
    specialSurface3 = DarkSpecialSurface3,
    specialSurface4 = DarkSpecialSurface4,
    specialSurface5 = DarkSpecialSurface5,
    specialSurface6 = DarkSpecialSurface6,
    specialSurface7 = DarkSpecialSurface7,
    specialSurface8 = DarkSpecialSurface8,
    specialSurface9 = DarkSpecialSurface9,
    specialSurface10 = DarkSpecialSurface10,
    specialSurface11 = DarkSpecialSurface11,
    specialSurface12 = DarkSpecialSurface12,
    specialSurface13 = DarkSpecialSurface13,
    specialSurface14 = DarkSpecialSurface14,
    specialSurface15 = DarkSpecialSurface15,
    specialSurface16 = DarkSpecialSurface16,
    specialGrey = DarkSpecialGrey,

    textPrimary = DarkTextPrimary,
    textPrimaryForceLight = LightTextPrimary,
    textSecondary = DarkTextSecondary,

    pricePrimary = DarkPricePrimary,
    priceSecondary = DarkPriceSecondary,
    priceSurfacePrimary = DarkPriceSurfacePrimary,
    priceSurfaceSecondary = DarkPriceSurfaceSecondary,

    syncBarColor1 = SyncBarColor1,
    syncBarColor2 = SyncBarColor2,
    syncBarColor3 = SyncBarColor3,
    syncBarColor4 = SyncBarColor4,
    syncBarColor5 = SyncBarColor5
)

private val extendedTypography = AppTypography()

val LocalExtendedColorScheme = staticCompositionLocalOf { extendedLightColorScheme }
val LocalExtendedTypography = staticCompositionLocalOf { extendedTypography }

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val extendedColorScheme = if (darkTheme) extendedDarkColorScheme else extendedLightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = extendedColorScheme.material.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    CompositionLocalProvider(
        LocalExtendedColorScheme provides extendedColorScheme,
        LocalExtendedTypography provides extendedTypography
    ) {
        MaterialTheme(
            colorScheme = extendedColorScheme.material,
            typography = extendedTypography.material,
            content = content
        )
    }
}

object AppTheme {
    val colors: AppColors
        @Composable
        get() = LocalExtendedColorScheme.current

    val typography: AppTypography
        @Composable
        get() = LocalExtendedTypography.current
}
