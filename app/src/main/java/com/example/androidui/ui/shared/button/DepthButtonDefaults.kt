package com.example.androidui.ui.shared.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.androidui.ui.theme.JetBrainsMonoFontFamily
import com.example.androidui.ui.theme.likeRed

/**
 * Semantic presets for embossed & debossed buttons.
 */
enum class ButtonPreset {
    Primary,
    Secondary,
    Surface,
    Destructive
}

/**
 * Sizing options for embossed & debossed buttons.
 */
enum class ButtonSize {
    Small,
    Medium,
    Large
}

/**
 * Color specification for solid Embossed and Debossed buttons.
 *
 * Normal (Embossed): Top-Left = highlightColor, Bottom-Right = shadowColor
 * Pressed (Debossed): Top-Left = shadowColor, Bottom-Right = highlightColor
 */
@Immutable
data class DepthButtonColors(
    val surfaceColor: Color,
    val contentColor: Color,
    val highlightColor: Color,
    val shadowColor: Color,
    val disabledSurfaceColor: Color,
    val disabledContentColor: Color,
    val disabledHighlightColor: Color,
    val disabledShadowColor: Color
) {
    fun surfaceColor(enabled: Boolean): Color =
        if (enabled) surfaceColor else disabledSurfaceColor

    fun contentColor(enabled: Boolean): Color =
        if (enabled) contentColor else disabledContentColor

    fun highlightColor(enabled: Boolean): Color =
        if (enabled) highlightColor else disabledHighlightColor

    fun shadowColor(enabled: Boolean): Color =
        if (enabled) shadowColor else disabledShadowColor
}

/**
 * Default styling, dimensions, and color schemes for Embossed & Debossed Buttons.
 */
object DepthButtonDefaults {
    val BevelWidthSmall: Dp = 1.5.dp
    val BevelWidthMedium: Dp = 2.dp
    val BevelWidthLarge: Dp = 2.5.dp

    val CornerRadiusSmall: Dp = 10.dp
    val CornerRadiusMedium: Dp = 14.dp
    val CornerRadiusLarge: Dp = 18.dp

    fun bevelWidth(size: ButtonSize): Dp = when (size) {
        ButtonSize.Small -> BevelWidthSmall
        ButtonSize.Medium -> BevelWidthMedium
        ButtonSize.Large -> BevelWidthLarge
    }

    fun cornerRadius(size: ButtonSize): Dp = when (size) {
        ButtonSize.Small -> CornerRadiusSmall
        ButtonSize.Medium -> CornerRadiusMedium
        ButtonSize.Large -> CornerRadiusLarge
    }

    fun minHeight(size: ButtonSize): Dp = when (size) {
        ButtonSize.Small -> 36.dp
        ButtonSize.Medium -> 48.dp
        ButtonSize.Large -> 56.dp
    }

    fun contentPadding(size: ButtonSize): PaddingValues = when (size) {
        ButtonSize.Small -> PaddingValues(horizontal = 14.dp, vertical = 8.dp)
        ButtonSize.Medium -> PaddingValues(horizontal = 20.dp, vertical = 12.dp)
        ButtonSize.Large -> PaddingValues(horizontal = 26.dp, vertical = 16.dp)
    }

    fun iconSize(size: ButtonSize): Dp = when (size) {
        ButtonSize.Small -> 16.dp
        ButtonSize.Medium -> 20.dp
        ButtonSize.Large -> 24.dp
    }

    @Composable
    @ReadOnlyComposable
    fun textStyle(size: ButtonSize): TextStyle = when (size) {
        ButtonSize.Small -> MaterialTheme.typography.labelMedium.copy(
            fontFamily = JetBrainsMonoFontFamily
        )
        ButtonSize.Medium -> MaterialTheme.typography.titleSmall.copy(
            fontFamily = JetBrainsMonoFontFamily
        )
        ButtonSize.Large -> MaterialTheme.typography.titleMedium.copy(
            fontFamily = JetBrainsMonoFontFamily
        )
    }

    @Composable
    @ReadOnlyComposable
    fun presetColors(preset: ButtonPreset): DepthButtonColors = when (preset) {
        ButtonPreset.Primary -> primaryColors()
        ButtonPreset.Secondary -> secondaryColors()
        ButtonPreset.Surface -> surfaceColors()
        ButtonPreset.Destructive -> destructiveColors()
    }

    @Composable
    @ReadOnlyComposable
    fun primaryColors(
        surfaceColor: Color = MaterialTheme.colorScheme.primary,
        contentColor: Color = MaterialTheme.colorScheme.onPrimary,
        highlightColor: Color = Color.White.copy(alpha = 0.45f),
        shadowColor: Color = Color.Black.copy(alpha = 0.40f)
    ): DepthButtonColors {
        return DepthButtonColors(
            surfaceColor = surfaceColor,
            contentColor = contentColor,
            highlightColor = highlightColor,
            shadowColor = shadowColor,
            disabledSurfaceColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
            disabledHighlightColor = Color.White.copy(alpha = 0.12f),
            disabledShadowColor = Color.Black.copy(alpha = 0.10f)
        )
    }

    @Composable
    @ReadOnlyComposable
    fun secondaryColors(
        surfaceColor: Color = MaterialTheme.colorScheme.secondary,
        contentColor: Color = MaterialTheme.colorScheme.onSecondary,
        highlightColor: Color = Color.White.copy(alpha = 0.45f),
        shadowColor: Color = Color.Black.copy(alpha = 0.38f)
    ): DepthButtonColors {
        return DepthButtonColors(
            surfaceColor = surfaceColor,
            contentColor = contentColor,
            highlightColor = highlightColor,
            shadowColor = shadowColor,
            disabledSurfaceColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
            disabledHighlightColor = Color.White.copy(alpha = 0.12f),
            disabledShadowColor = Color.Black.copy(alpha = 0.10f)
        )
    }

    @Composable
    @ReadOnlyComposable
    fun surfaceColors(
        surfaceColor: Color = MaterialTheme.colorScheme.surfaceContainerHigh,
        contentColor: Color = MaterialTheme.colorScheme.onSurface,
        highlightColor: Color = Color.White.copy(alpha = 0.85f),
        shadowColor: Color = Color.Black.copy(alpha = 0.28f)
    ): DepthButtonColors {
        return DepthButtonColors(
            surfaceColor = surfaceColor,
            contentColor = contentColor,
            highlightColor = highlightColor,
            shadowColor = shadowColor,
            disabledSurfaceColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.35f),
            disabledHighlightColor = Color.White.copy(alpha = 0.20f),
            disabledShadowColor = Color.Black.copy(alpha = 0.08f)
        )
    }

    @Composable
    @ReadOnlyComposable
    fun destructiveColors(
        surfaceColor: Color = likeRed,
        contentColor: Color = Color.White,
        highlightColor: Color = Color.White.copy(alpha = 0.45f),
        shadowColor: Color = Color.Black.copy(alpha = 0.45f)
    ): DepthButtonColors {
        return DepthButtonColors(
            surfaceColor = surfaceColor,
            contentColor = contentColor,
            highlightColor = highlightColor,
            shadowColor = shadowColor,
            disabledSurfaceColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
            disabledHighlightColor = Color.White.copy(alpha = 0.12f),
            disabledShadowColor = Color.Black.copy(alpha = 0.10f)
        )
    }
}

