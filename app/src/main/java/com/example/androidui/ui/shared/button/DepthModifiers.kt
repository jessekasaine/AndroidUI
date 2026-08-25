package com.example.androidui.ui.shared.button

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

/**
 * Immutable style specification for a single shadow effect.
 *
 * @property radius Blur radius of the shadow.
 * @property spread Size expansion / spread of the shadow geometry.
 * @property color Tint color of the cast shadow.
 * @property offset Vertical and horizontal offset displacement.
 * @property alpha Opacity multiplier for the shadow.
 */
@Immutable
data class ShadowStyle(
    val radius: Dp = 0.dp,
    val spread: Dp = 0.dp,
    val color: Color = Color.Unspecified,
    val offset: DpOffset = DpOffset.Zero,
    val alpha: Float = 1.0f
) {
    /**
     * Converts this [ShadowStyle] specification into Compose [Shadow].
     */
    fun toShadow(): Shadow = Shadow(
        radius = radius,
        spread = spread,
        color = color,
        offset = offset,
        alpha = alpha
    )
}

/**
 * Encapsulates the complete depth shadow scheme for an element across raised and pressed states.
 *
 * @property dropShadow Outer drop shadow cast behind the element in raised state.
 * @property topInnerHighlight Soft light-facing inner highlight along the top edge in raised state.
 * @property bottomInnerShadow Ambient inner shadow along the bottom edge in raised state.
 * @property pressedTopInnerShadow Inset cavity shadow along the top edge when pressed/sunken.
 * @property pressedBottomInnerHighlight Subtle inner highlight along the bottom edge when pressed/sunken.
 */
@Immutable
data class DepthShadowStyle(
    val dropShadow: ShadowStyle? = null,
    val topInnerHighlight: ShadowStyle? = null,
    val bottomInnerShadow: ShadowStyle? = null,
    val pressedTopInnerShadow: ShadowStyle? = null,
    val pressedBottomInnerHighlight: ShadowStyle? = null
)

/**
 * Applies a [ShadowStyle] as an outer drop shadow behind the content using [Modifier.dropShadow].
 */
fun Modifier.dropShadow(
    shape: Shape,
    style: ShadowStyle
): Modifier = if (style.color != Color.Unspecified && style.alpha > 0f) {
    this.dropShadow(
        shape = shape,
        shadow = style.toShadow()
    )
} else {
    this
}

/**
 * Applies a [ShadowStyle] as an inner shadow on top of the content using [Modifier.innerShadow].
 */
fun Modifier.innerShadow(
    shape: Shape,
    style: ShadowStyle
): Modifier = if (style.color != Color.Unspecified && style.alpha > 0f) {
    this.innerShadow(
        shape = shape,
        shadow = style.toShadow()
    )
} else {
    this
}

/**
 * Applies outer drop shadow styling using [DepthShadowStyle] across raised and pressed states.
 */
fun Modifier.embossedDepth(
    shape: Shape,
    shadowStyle: DepthShadowStyle,
    isPressed: Boolean = false,
    enabled: Boolean = true
): Modifier {
    var modifier: Modifier = this

    if (!isPressed && enabled) {
        shadowStyle.dropShadow?.let {
            modifier = modifier.dropShadow(shape = shape, style = it)
        }
    }

    return modifier
}

/**
 * Applies inner bevel shadow styling using [DepthShadowStyle] across raised and pressed states.
 */
fun Modifier.embossedInnerDepth(
    shape: Shape,
    shadowStyle: DepthShadowStyle,
    isPressed: Boolean = false,
    enabled: Boolean = true
): Modifier {
    var modifier: Modifier = this

    if (!isPressed || !enabled) {
        shadowStyle.topInnerHighlight?.let {
            modifier = modifier.innerShadow(shape = shape, style = it)
        }
        shadowStyle.bottomInnerShadow?.let {
            modifier = modifier.innerShadow(shape = shape, style = it)
        }
    } else {
        shadowStyle.pressedTopInnerShadow?.let {
            modifier = modifier.innerShadow(shape = shape, style = it)
        }
        shadowStyle.pressedBottomInnerHighlight?.let {
            modifier = modifier.innerShadow(shape = shape, style = it)
        }
    }

    return modifier
}

/**
 * Draws a soft inner highlight along the top perimeter of the shape using [Modifier.innerShadow].
 */
fun Modifier.topInnerHighlight(
    highlightColor: Color,
    shape: Shape,
    radius: Dp = 3.dp,
    spread: Dp = 1.dp,
    offset: DpOffset = DpOffset(x = 0.dp, y = 1.5.dp),
    alpha: Float = 1.0f
): Modifier = this.innerShadow(
    shape = shape,
    style = ShadowStyle(
        radius = radius,
        spread = spread,
        color = highlightColor,
        offset = offset,
        alpha = alpha
    )
)

/**
 * Convenience overload of [topInnerHighlight] taking a [cornerRadius] in [Dp].
 */
fun Modifier.topInnerHighlight(
    highlightColor: Color,
    cornerRadius: Dp,
    radius: Dp = 3.dp,
    spread: Dp = 1.dp,
    offset: DpOffset = DpOffset(x = 0.dp, y = 1.5.dp),
    alpha: Float = 1.0f
): Modifier = topInnerHighlight(
    highlightColor = highlightColor,
    shape = RoundedCornerShape(cornerRadius),
    radius = radius,
    spread = spread,
    offset = offset,
    alpha = alpha
)

/**
 * Draws the outer drop shadow for the embossed depth effect using [ShadowStyle].
 */
fun Modifier.embossedDropShadow(
    isPressed: Boolean,
    shadowColor: Color,
    shape: Shape,
    depth: Dp = 2.dp,
    radius: Dp = depth * 2,
    spread: Dp = 0.dp
): Modifier = if (!isPressed && depth > 0.dp) {
    this.dropShadow(
        shape = shape,
        style = ShadowStyle(
            radius = radius,
            spread = spread,
            color = shadowColor.copy(alpha = shadowColor.alpha * 0.45f),
            offset = DpOffset(x = 0.dp, y = depth)
        )
    )
} else {
    this
}

/**
 * Convenience overload of [embossedDropShadow] taking a [cornerRadius] in [Dp].
 */
fun Modifier.embossedDropShadow(
    isPressed: Boolean,
    shadowColor: Color,
    cornerRadius: Dp,
    depth: Dp = 2.dp,
    radius: Dp = depth * 2,
    spread: Dp = 0.dp
): Modifier = embossedDropShadow(
    isPressed = isPressed,
    shadowColor = shadowColor,
    shape = RoundedCornerShape(cornerRadius),
    depth = depth,
    radius = radius,
    spread = spread
)

/**
 * Draws the dual-tone inner bevel shadows (top highlight + bottom shadow) using [ShadowStyle].
 */
fun Modifier.embossedInnerShadow(
    isPressed: Boolean,
    highlightColor: Color,
    shadowColor: Color,
    shape: Shape,
    depth: Dp = 2.dp
): Modifier {
    val style = DepthShadowStyle(
        topInnerHighlight = ShadowStyle(
            radius = 2.dp,
            spread = 1.dp,
            color = highlightColor,
            offset = DpOffset(x = 0.dp, y = 1.5.dp)
        ),
        bottomInnerShadow = ShadowStyle(
            radius = 2.dp,
            spread = 1.dp,
            color = shadowColor.copy(alpha = shadowColor.alpha * 0.35f),
            offset = DpOffset(x = 0.dp, y = (-1.5).dp)
        ),
        pressedTopInnerShadow = ShadowStyle(
            radius = 3.dp,
            spread = 1.5.dp,
            color = shadowColor.copy(alpha = (shadowColor.alpha * 0.65f).coerceAtMost(1f)),
            offset = DpOffset(x = 0.dp, y = depth.coerceAtLeast(2.dp))
        ),
        pressedBottomInnerHighlight = ShadowStyle(
            radius = 2.dp,
            spread = 1.dp,
            color = highlightColor.copy(alpha = highlightColor.alpha * 0.35f),
            offset = DpOffset(x = 0.dp, y = (-1.5).dp)
        )
    )
    return this.embossedInnerDepth(shape = shape, shadowStyle = style, isPressed = isPressed)
}

/**
 * Convenience overload of [embossedInnerShadow] taking a [cornerRadius] in [Dp].
 */
fun Modifier.embossedInnerShadow(
    isPressed: Boolean,
    highlightColor: Color,
    shadowColor: Color,
    cornerRadius: Dp,
    depth: Dp = 2.dp
): Modifier = embossedInnerShadow(
    isPressed = isPressed,
    highlightColor = highlightColor,
    shadowColor = shadowColor,
    shape = RoundedCornerShape(cornerRadius),
    depth = depth
)

/**
 * Applies a complete embossed depth effect consisting of an outer drop shadow,
 * container background, and dual-tone inner shadows.
 */
fun Modifier.embossedDepth(
    isPressed: Boolean,
    highlightColor: Color,
    shadowColor: Color,
    shape: Shape,
    containerColor: Color,
    depth: Dp = 2.dp
): Modifier = this
    .embossedDropShadow(
        isPressed = isPressed,
        shadowColor = shadowColor,
        shape = shape,
        depth = depth
    )
    .background(containerColor, shape)
    .embossedInnerShadow(
        isPressed = isPressed,
        highlightColor = highlightColor,
        shadowColor = shadowColor,
        shape = shape,
        depth = depth
    )

/**
 * Convenience overload of [embossedDepth] taking a [cornerRadius] in [Dp] and container [Color].
 */
fun Modifier.embossedDepth(
    isPressed: Boolean,
    highlightColor: Color,
    shadowColor: Color,
    cornerRadius: Dp,
    containerColor: Color,
    depth: Dp = 2.dp
): Modifier = embossedDepth(
    isPressed = isPressed,
    highlightColor = highlightColor,
    shadowColor = shadowColor,
    shape = RoundedCornerShape(cornerRadius),
    containerColor = containerColor,
    depth = depth
)

// End of Depth and Embossed Shadow Modifier extensions suite.
