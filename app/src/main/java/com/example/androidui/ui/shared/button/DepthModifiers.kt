package com.example.androidui.ui.shared.button

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Draws a subtle embossed/neumorphic surface with soft physical depth underneath,
 * a directional top-left light highlight, and a directional bottom-right dark shadow.
 */
fun Modifier.subtleEmbossedSurface(
    surfaceColor: Color,
    highlightColor: Color,
    shadowGradientColor: Color,
    depthShadowColor: Color,
    cornerRadius: Dp,
    depth: Dp,
    currentOffsetY: Dp = 0.dp
): Modifier = this.drawWithCache {
    val cornerRadiusPx = cornerRadius.toPx()
    val depthPx = depth.toPx()
    val currentOffsetYPx = currentOffsetY.toPx()

    // Remaining physical depth underneath as button is pressed down
    val remainingDepthY = (depthPx - currentOffsetYPx).coerceAtLeast(0f)
    val shadowOffsetRatio = if (depthPx > 0f) (remainingDepthY / depthPx) else 0f

    val highlightGradient = Brush.linearGradient(
        colors = listOf(
            highlightColor,
            highlightColor.copy(alpha = 0.0f)
        ),
        start = Offset.Zero,
        end = Offset(size.width, size.height)
    )

    val shadowGradient = Brush.linearGradient(
        colors = listOf(
            shadowGradientColor,
            shadowGradientColor.copy(alpha = 0.0f)
        ),
        start = Offset(size.width, size.height),
        end = Offset.Zero
    )

    onDrawBehind {
        // 1. Dark depth layer underneath the button (compresses on press)
        if (remainingDepthY > 0f && depthShadowColor.alpha > 0f) {
            drawRoundRect(
                color = depthShadowColor.copy(alpha = depthShadowColor.alpha * (0.6f + 0.4f * shadowOffsetRatio)),
                topLeft = Offset(
                    x = 0f,
                    y = remainingDepthY
                ),
                size = Size(
                    width = size.width,
                    height = size.height
                ),
                cornerRadius = CornerRadius(cornerRadiusPx)
            )
        }

        // 2. Main surface
        drawRoundRect(
            color = surfaceColor,
            cornerRadius = CornerRadius(cornerRadiusPx)
        )

        // 3. Directional light coming from top-left
        if (highlightColor.alpha > 0f) {
            drawRoundRect(
                brush = highlightGradient,
                cornerRadius = CornerRadius(cornerRadiusPx)
            )
        }

        // 4. Darker lower-right edge
        if (shadowGradientColor.alpha > 0f) {
            drawRoundRect(
                brush = shadowGradient,
                cornerRadius = CornerRadius(cornerRadiusPx)
            )
        }
    }
}

