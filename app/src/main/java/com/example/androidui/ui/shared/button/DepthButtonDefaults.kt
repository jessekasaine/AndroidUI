package com.example.androidui.ui.shared.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
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
 * Represents the color specification for a depth button in enabled and disabled states.
 */
@Immutable
data class DepthButtonColors(
    val containerColor: Color,
    val contentColor: Color,
    val topHighlightColor: Color,
    val bottomShadowColor: Color,
    val disabledContainerColor: Color,
    val disabledContentColor: Color,
    val disabledTopHighlightColor: Color,
    val disabledBottomShadowColor: Color
) {
    /**
     * Returns the active container color based on the [enabled] state.
     */
    fun containerColor(enabled: Boolean): Color =
        if (enabled) containerColor else disabledContainerColor

    /**
     * Returns the active content color based on the [enabled] state.
     */
    fun contentColor(enabled: Boolean): Color =
        if (enabled) contentColor else disabledContentColor

    /**
     * Returns the top highlight color based on the [enabled] state.
     */
    fun topHighlightColor(enabled: Boolean): Color =
        if (enabled) topHighlightColor else disabledTopHighlightColor

    /**
     * Returns the bottom shadow color based on the [enabled] state.
     */
    fun bottomShadowColor(enabled: Boolean): Color =
        if (enabled) bottomShadowColor else disabledBottomShadowColor
}

/**
 * Complete styling specification for a depth button, including colors, shadows, geometry, and typography.
 *
 * @property colors Colors used for container, content, and depth highlights.
 * @property shadowStyle Specification for outer drop shadows and inner bevel shadows.
 * @property depth Vertical elevation depth.
 * @property cornerRadius Corner radius of the button.
 * @property contentPadding Padding values inside the button container.
 * @property textStyle Typography style applied to text content within the button.
 */
@Immutable
data class DepthButtonStyle(
    val colors: DepthButtonColors,
    val shadowStyle: DepthShadowStyle,
    val depth: Dp = DepthButtonDefaults.EmbossedDepthDefault,
    val cornerRadius: Dp = DepthButtonDefaults.CornerRadiusMedium,
    val contentPadding: PaddingValues = PaddingValues(horizontal = 20.dp, vertical = 10.dp),
    val textStyle: TextStyle = TextStyle.Default
)

/**
 * Default styling, dimensions, and color schemes for Depth Buttons.
 */
object DepthButtonDefaults {
    val EmbossedDepthDefault: Dp = 2.dp
    val EmbossedDepthSmall: Dp = 1.5.dp
    val EmbossedDepthLarge: Dp = 3.dp
    val CornerRadiusSmall: Dp = 10.dp
    val CornerRadiusMedium: Dp = 14.dp
    val CornerRadiusLarge: Dp = 18.dp

    /**
     * Builds a [DepthShadowStyle] given depth distance, shadow color, and highlight color.
     */
    fun shadowStyle(
        depth: Dp = EmbossedDepthDefault,
        shadowColor: Color,
        highlightColor: Color
    ): DepthShadowStyle = DepthShadowStyle(
        dropShadow = ShadowStyle(
            radius = depth * 2,
            spread = 0.dp,
            color = shadowColor.copy(alpha = shadowColor.alpha * 0.45f),
            offset = androidx.compose.ui.unit.DpOffset(x = 0.dp, y = depth)
        ),
        topInnerHighlight = ShadowStyle(
            radius = 2.dp,
            spread = 1.dp,
            color = highlightColor,
            offset = androidx.compose.ui.unit.DpOffset(x = 0.dp, y = 1.5.dp)
        ),
        bottomInnerShadow = ShadowStyle(
            radius = 2.dp,
            spread = 1.dp,
            color = shadowColor.copy(alpha = shadowColor.alpha * 0.35f),
            offset = androidx.compose.ui.unit.DpOffset(x = 0.dp, y = (-1.5).dp)
        ),
        pressedTopInnerShadow = ShadowStyle(
            radius = 3.dp,
            spread = 1.5.dp,
            color = shadowColor.copy(alpha = (shadowColor.alpha * 0.65f).coerceAtMost(1f)),
            offset = androidx.compose.ui.unit.DpOffset(x = 0.dp, y = depth.coerceAtLeast(2.dp))
        ),
        pressedBottomInnerHighlight = ShadowStyle(
            radius = 2.dp,
            spread = 1.dp,
            color = highlightColor.copy(alpha = highlightColor.alpha * 0.35f),
            offset = androidx.compose.ui.unit.DpOffset(x = 0.dp, y = (-1.5).dp)
        )
    )

    /**
     * Builds a complete [DepthButtonStyle] for the given [preset] and [size].
     */
    @Composable
    @ReadOnlyComposable
    fun style(
        preset: ButtonPreset = ButtonPreset.Primary,
        size: ButtonSize = ButtonSize.Medium
    ): DepthButtonStyle {
        val colors = presetColors(preset)
        val depth = when (size) {
            ButtonSize.Small -> EmbossedDepthSmall
            ButtonSize.Large -> EmbossedDepthLarge
            ButtonSize.Medium -> EmbossedDepthDefault
        }
        return DepthButtonStyle(
            colors = colors,
            shadowStyle = shadowStyle(
                depth = depth,
                shadowColor = colors.bottomShadowColor,
                highlightColor = colors.topHighlightColor
            ),
            depth = depth,
            cornerRadius = cornerRadius(size),
            contentPadding = contentPadding(size),
            textStyle = textStyle(size)
        )
    }

    /**
     * Returns standard min-height for the given [size].
     */
    fun minHeight(size: ButtonSize): Dp = when (size) {
        ButtonSize.Small -> 36.dp
        ButtonSize.Medium -> 48.dp
        ButtonSize.Large -> 58.dp
    }

    /**
     * Returns standard horizontal and vertical padding for the given [size].
     */
    fun contentPadding(size: ButtonSize): PaddingValues = when (size) {
        ButtonSize.Small -> PaddingValues(horizontal = 14.dp, vertical = 6.dp)
        ButtonSize.Medium -> PaddingValues(horizontal = 20.dp, vertical = 10.dp)
        ButtonSize.Large -> PaddingValues(horizontal = 26.dp, vertical = 14.dp)
    }

    /**
     * Returns corner radius for the given [size].
     */
    fun cornerRadius(size: ButtonSize): Dp = when (size) {
        ButtonSize.Small -> CornerRadiusSmall
        ButtonSize.Medium -> CornerRadiusMedium
        ButtonSize.Large -> CornerRadiusLarge
    }

    /**
     * Returns icon size for the given [size].
     */
    fun iconSize(size: ButtonSize): Dp = when (size) {
        ButtonSize.Small -> 16.dp
        ButtonSize.Medium -> 20.dp
        ButtonSize.Large -> 24.dp
    }

    /**
     * Returns text style for the given [size].
     */
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

    /**
     * Returns the [DepthButtonColors] for the specified [preset].
     */
    @Composable
    @ReadOnlyComposable
    fun presetColors(preset: ButtonPreset): DepthButtonColors = when (preset) {
        ButtonPreset.Primary -> primaryColors()
        ButtonPreset.Secondary -> secondaryColors()
        ButtonPreset.Surface -> surfaceColors()
        ButtonPreset.Destructive -> destructiveColors()
    }

    /**
     * Semantic Primary color specification.
     */
    @Composable
    @ReadOnlyComposable
    fun primaryColors(
        containerColor: Color = MaterialTheme.colorScheme.primary,
        contentColor: Color = MaterialTheme.colorScheme.onPrimary,
        topHighlightColor: Color = Color.White.copy(alpha = 0.35f),
        bottomShadowColor: Color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.9f)
            .compositeOver(Color.Black.copy(alpha = 0.55f))
    ): DepthButtonColors {
        return DepthButtonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            topHighlightColor = topHighlightColor,
            bottomShadowColor = bottomShadowColor,
            disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
            disabledTopHighlightColor = Color.White.copy(alpha = 0.10f),
            disabledBottomShadowColor = Color.Black.copy(alpha = 0.15f)
        )
    }

    /**
     * Semantic Secondary color specification.
     */
    @Composable
    @ReadOnlyComposable
    fun secondaryColors(
        containerColor: Color = MaterialTheme.colorScheme.secondary,
        contentColor: Color = MaterialTheme.colorScheme.onSecondary,
        topHighlightColor: Color = Color.White.copy(alpha = 0.30f),
        bottomShadowColor: Color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.9f)
            .compositeOver(Color.Black.copy(alpha = 0.5f))
    ): DepthButtonColors {
        return DepthButtonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            topHighlightColor = topHighlightColor,
            bottomShadowColor = bottomShadowColor,
            disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
            disabledTopHighlightColor = Color.White.copy(alpha = 0.10f),
            disabledBottomShadowColor = Color.Black.copy(alpha = 0.15f)
        )
    }

    /**
     * Semantic Surface / Neutral color specification.
     */
    @Composable
    @ReadOnlyComposable
    fun surfaceColors(
        containerColor: Color = MaterialTheme.colorScheme.surfaceContainerHigh,
        contentColor: Color = MaterialTheme.colorScheme.onSurface,
        topHighlightColor: Color = Color.White.copy(alpha = 0.45f),
        bottomShadowColor: Color = MaterialTheme.colorScheme.scrim.copy(alpha = 0.32f)
    ): DepthButtonColors {
        return DepthButtonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            topHighlightColor = topHighlightColor,
            bottomShadowColor = bottomShadowColor,
            disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.35f),
            disabledTopHighlightColor = Color.White.copy(alpha = 0.08f),
            disabledBottomShadowColor = Color.Black.copy(alpha = 0.10f)
        )
    }

    /**
     * Semantic Destructive / Danger color specification.
     */
    @Composable
    @ReadOnlyComposable
    fun destructiveColors(
        containerColor: Color = likeRed,
        contentColor: Color = Color.White,
        topHighlightColor: Color = Color.White.copy(alpha = 0.35f),
        bottomShadowColor: Color = Color(0xFF7A0B0B)
    ): DepthButtonColors {
        return DepthButtonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            topHighlightColor = topHighlightColor,
            bottomShadowColor = bottomShadowColor,
            disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
            disabledTopHighlightColor = Color.White.copy(alpha = 0.10f),
            disabledBottomShadowColor = Color.Black.copy(alpha = 0.15f)
        )
    }
}
