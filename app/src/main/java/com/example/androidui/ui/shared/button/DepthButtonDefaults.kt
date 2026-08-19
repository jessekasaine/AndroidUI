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
 * Semantic presets for depth buttons.
 */
enum class ButtonPreset {
    Primary,
    Secondary,
    Surface,
    Destructive
}

/**
 * Sizing options for depth buttons.
 */
enum class ButtonSize {
    Small,
    Medium,
    Large
}

/**
 * Color specification for subtle neumorphic embossed buttons.
 * Uses top-left directional highlight and bottom-right directional shadow
 * over a physical depth shadow layer.
 */
@Immutable
data class DepthButtonColors(
    val surfaceColor: Color,
    val contentColor: Color,
    val highlightColor: Color,
    val shadowGradientColor: Color,
    val depthShadowColor: Color,
    val disabledSurfaceColor: Color,
    val disabledContentColor: Color,
    val disabledHighlightColor: Color,
    val disabledShadowGradientColor: Color,
    val disabledDepthShadowColor: Color
) {
    fun surfaceColor(enabled: Boolean): Color =
        if (enabled) surfaceColor else disabledSurfaceColor

    fun contentColor(enabled: Boolean): Color =
        if (enabled) contentColor else disabledContentColor

    fun highlightColor(enabled: Boolean): Color =
        if (enabled) highlightColor else disabledHighlightColor

    fun shadowGradientColor(enabled: Boolean): Color =
        if (enabled) shadowGradientColor else disabledShadowGradientColor

    fun depthShadowColor(enabled: Boolean): Color =
        if (enabled) depthShadowColor else disabledDepthShadowColor
}

/**
 * Default styling, dimensions, and color schemes for Depth & Embossed Buttons.
 */
object DepthButtonDefaults {
    val EmbossedDepthSmall: Dp = 2.5.dp
    val EmbossedDepthMedium: Dp = 3.5.dp
    val EmbossedDepthLarge: Dp = 5.dp

    val PressTravelSmall: Dp = 1.5.dp
    val PressTravelMedium: Dp = 2.dp
    val PressTravelLarge: Dp = 3.dp

    val CornerRadiusSmall: Dp = 12.dp
    val CornerRadiusMedium: Dp = 16.dp
    val CornerRadiusLarge: Dp = 20.dp

    fun depth(size: ButtonSize): Dp = when (size) {
        ButtonSize.Small -> EmbossedDepthSmall
        ButtonSize.Medium -> EmbossedDepthMedium
        ButtonSize.Large -> EmbossedDepthLarge
    }

    fun pressTravel(size: ButtonSize): Dp = when (size) {
        ButtonSize.Small -> PressTravelSmall
        ButtonSize.Medium -> PressTravelMedium
        ButtonSize.Large -> PressTravelLarge
    }

    fun cornerRadius(size: ButtonSize): Dp = when (size) {
        ButtonSize.Small -> CornerRadiusSmall
        ButtonSize.Medium -> CornerRadiusMedium
        ButtonSize.Large -> CornerRadiusLarge
    }

    fun minHeight(size: ButtonSize): Dp = when (size) {
        ButtonSize.Small -> 36.dp
        ButtonSize.Medium -> 48.dp
        ButtonSize.Large -> 58.dp
    }

    fun contentPadding(size: ButtonSize): PaddingValues = when (size) {
        ButtonSize.Small -> PaddingValues(horizontal = 14.dp, vertical = 8.dp)
        ButtonSize.Medium -> PaddingValues(horizontal = 22.dp, vertical = 12.dp)
        ButtonSize.Large -> PaddingValues(horizontal = 28.dp, vertical = 16.dp)
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
        highlightColor: Color = Color.White.copy(alpha = 0.35f),
        shadowGradientColor: Color = Color.Black.copy(alpha = 0.28f),
        depthShadowColor: Color = Color.Black.copy(alpha = 0.22f)
    ): DepthButtonColors {
        return DepthButtonColors(
            surfaceColor = surfaceColor,
            contentColor = contentColor,
            highlightColor = highlightColor,
            shadowGradientColor = shadowGradientColor,
            depthShadowColor = depthShadowColor,
            disabledSurfaceColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
            disabledHighlightColor = Color.White.copy(alpha = 0.10f),
            disabledShadowGradientColor = Color.Transparent,
            disabledDepthShadowColor = Color.Transparent
        )
    }

    @Composable
    @ReadOnlyComposable
    fun secondaryColors(
        surfaceColor: Color = MaterialTheme.colorScheme.secondary,
        contentColor: Color = MaterialTheme.colorScheme.onSecondary,
        highlightColor: Color = Color.White.copy(alpha = 0.32f),
        shadowGradientColor: Color = Color.Black.copy(alpha = 0.24f),
        depthShadowColor: Color = Color.Black.copy(alpha = 0.20f)
    ): DepthButtonColors {
        return DepthButtonColors(
            surfaceColor = surfaceColor,
            contentColor = contentColor,
            highlightColor = highlightColor,
            shadowGradientColor = shadowGradientColor,
            depthShadowColor = depthShadowColor,
            disabledSurfaceColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
            disabledHighlightColor = Color.White.copy(alpha = 0.10f),
            disabledShadowGradientColor = Color.Transparent,
            disabledDepthShadowColor = Color.Transparent
        )
    }

    @Composable
    @ReadOnlyComposable
    fun surfaceColors(
        surfaceColor: Color = MaterialTheme.colorScheme.surfaceContainerHigh,
        contentColor: Color = MaterialTheme.colorScheme.onSurface,
        highlightColor: Color = Color.White.copy(alpha = 0.70f),
        shadowGradientColor: Color = Color.Black.copy(alpha = 0.16f),
        depthShadowColor: Color = Color.Black.copy(alpha = 0.14f)
    ): DepthButtonColors {
        return DepthButtonColors(
            surfaceColor = surfaceColor,
            contentColor = contentColor,
            highlightColor = highlightColor,
            shadowGradientColor = shadowGradientColor,
            depthShadowColor = depthShadowColor,
            disabledSurfaceColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.35f),
            disabledHighlightColor = Color.White.copy(alpha = 0.10f),
            disabledShadowGradientColor = Color.Transparent,
            disabledDepthShadowColor = Color.Transparent
        )
    }

    @Composable
    @ReadOnlyComposable
    fun destructiveColors(
        surfaceColor: Color = likeRed,
        contentColor: Color = Color.White,
        highlightColor: Color = Color.White.copy(alpha = 0.35f),
        shadowGradientColor: Color = Color.Black.copy(alpha = 0.30f),
        depthShadowColor: Color = Color(0xFF5A0808).copy(alpha = 0.40f)
    ): DepthButtonColors {
        return DepthButtonColors(
            surfaceColor = surfaceColor,
            contentColor = contentColor,
            highlightColor = highlightColor,
            shadowGradientColor = shadowGradientColor,
            depthShadowColor = depthShadowColor,
            disabledSurfaceColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
            disabledHighlightColor = Color.White.copy(alpha = 0.10f),
            disabledShadowGradientColor = Color.Transparent,
            disabledDepthShadowColor = Color.Transparent
        )
    }
}
