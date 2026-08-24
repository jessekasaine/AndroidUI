package com.example.androidui.ui.shared.button

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
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
 * Draws a soft inner highlight along the top perimeter of the shape using [Modifier.innerShadow].
 *
 * @param highlightColor Color of the inner highlight.
 * @param shape Shape to constrain the inner shadow geometry.
 * @param radius Blur radius of the highlight shadow.
 * @param spread Spread parameter that expands the highlight.
 * @param offset Vertical and horizontal offset of the highlight.
 * @param alpha Opacity of the highlight.
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
    shadow = Shadow(
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
 * Draws the outer drop shadow for the embossed depth effect using Compose [Modifier.dropShadow].
 * When [isPressed] is true, the drop shadow collapses for tactile button-press feedback.
 *
 * @param isPressed Whether the element is in a pressed/sunken state.
 * @param shadowColor Color of the cast drop shadow.
 * @param shape Shape geometry of the shadow.
 * @param depth Vertical elevation depth distance.
 * @param radius Blur softness radius of the shadow.
 * @param spread Size expansion of the shadow geometry.
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
        shadow = Shadow(
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
 * Draws the dual-tone inner bevel shadows (top highlight + bottom shadow) using Compose [Modifier.innerShadow].
 * Inverts the depth shading when [isPressed] is true to produce a realistic sunken/concave appearance.
 *
 * Note: [Modifier.innerShadow] draws on top of the content, so apply this modifier after background drawing.
 *
 * @param isPressed Whether the element is currently pressed.
 * @param highlightColor Color for the light-facing bevel highlight.
 * @param shadowColor Color for the ambient/deep bevel shadow.
 * @param shape Shape geometry of the inner shadows.
 * @param depth Depth offset amount for the bevel edges.
 */
fun Modifier.embossedInnerShadow(
    isPressed: Boolean,
    highlightColor: Color,
    shadowColor: Color,
    shape: Shape,
    depth: Dp = 2.dp
): Modifier {
    return if (!isPressed) {
        // Raised state: Top inner highlight + bottom inner shadow
        this
            .innerShadow(
                shape = shape,
                shadow = Shadow(
                    radius = 2.dp,
                    spread = 1.dp,
                    color = highlightColor,
                    offset = DpOffset(x = 0.dp, y = 1.5.dp)
                )
            )
            .innerShadow(
                shape = shape,
                shadow = Shadow(
                    radius = 2.dp,
                    spread = 1.dp,
                    color = shadowColor.copy(alpha = shadowColor.alpha * 0.35f),
                    offset = DpOffset(x = 0.dp, y = (-1.5).dp)
                )
            )
    } else {
        // Sunken / Pressed state: Inverted bevel with top cavity shadow + subtle bottom highlight
        this
            .innerShadow(
                shape = shape,
                shadow = Shadow(
                    radius = 3.dp,
                    spread = 1.5.dp,
                    color = shadowColor.copy(alpha = (shadowColor.alpha * 0.65f).coerceAtMost(1f)),
                    offset = DpOffset(x = 0.dp, y = depth.coerceAtLeast(2.dp))
                )
            )
            .innerShadow(
                shape = shape,
                shadow = Shadow(
                    radius = 2.dp,
                    spread = 1.dp,
                    color = highlightColor.copy(alpha = highlightColor.alpha * 0.35f),
                    offset = DpOffset(x = 0.dp, y = (-1.5).dp)
                )
            )
    }
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
 * Applies a complete embossed depth effect consisting of an outer [dropShadow],
 * [background], and dual-tone [innerShadow] bevels.
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

/**
 * Dual-tone embossed modifier that chains [embossedDropShadow] and [embossedInnerShadow].
 */
fun Modifier.embossedDepth(
    isPressed: Boolean,
    highlightColor: Color,
    shadowColor: Color,
    shape: Shape,
    depth: Dp = 2.dp
): Modifier = this
    .embossedDropShadow(
        isPressed = isPressed,
        shadowColor = shadowColor,
        shape = shape,
        depth = depth
    )
    .embossedInnerShadow(
        isPressed = isPressed,
        highlightColor = highlightColor,
        shadowColor = shadowColor,
        shape = shape,
        depth = depth
    )

/**
 * Convenience overload of [embossedDepth] taking a [cornerRadius] in [Dp].
 */
fun Modifier.embossedDepth(
    isPressed: Boolean,
    highlightColor: Color,
    shadowColor: Color,
    cornerRadius: Dp,
    depth: Dp = 2.dp
): Modifier = embossedDepth(
    isPressed = isPressed,
    highlightColor = highlightColor,
    shadowColor = shadowColor,
    shape = RoundedCornerShape(cornerRadius),
    depth = depth
)
