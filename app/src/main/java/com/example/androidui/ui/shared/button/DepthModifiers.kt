package com.example.androidui.ui.shared.button

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.unit.Dp

/**
 * Draws a solid Embossed (raised) or Debossed (sunken) surface using crisp solid-color bevel edges.
 *
 * - Normal (unpressed): Top-Left highlight border + Bottom-Right shadow border (Embossed).
 * - Pressed: Top-Left shadow border + Bottom-Right highlight border (Debossed).
 * - Gradients and animations are entirely omitted for a clean, snappy, normal button look.
 */
fun Modifier.embossedSurface(
    isPressed: Boolean,
    surfaceColor: Color,
    highlightColor: Color,
    shadowColor: Color,
    cornerRadius: Dp,
    bevelWidth: Dp
): Modifier = this.drawWithCache {
    val cornerRadiusPx = cornerRadius.toPx()
    val bevelWidthPx = bevelWidth.toPx()

    val topLeftClip = Path().apply {
        moveTo(0f, 0f)
        lineTo(size.width, 0f)
        lineTo(0f, size.height)
        close()
    }

    val bottomRightClip = Path().apply {
        moveTo(size.width, 0f)
        lineTo(size.width, size.height)
        lineTo(0f, size.height)
        close()
    }

    val inset = bevelWidthPx / 2f
    val stroke = Stroke(width = bevelWidthPx)
    val innerRadius = (cornerRadiusPx - inset).coerceAtLeast(0f)

    onDrawBehind {
        // 1. Solid surface background
        drawRoundRect(
            color = surfaceColor,
            cornerRadius = CornerRadius(cornerRadiusPx)
        )

        if (bevelWidthPx > 0f) {
            // Determine bevel colors based on pressed state:
            // Embossed (normal): Top-Left = Highlight, Bottom-Right = Shadow
            // Debossed (pressed): Top-Left = Shadow, Bottom-Right = Highlight
            val topLeftColor = if (isPressed) shadowColor else highlightColor
            val bottomRightColor = if (isPressed) highlightColor else shadowColor

            val borderRectOffset = Offset(inset, inset)
            val borderRectSize = Size(
                width = (size.width - bevelWidthPx).coerceAtLeast(0f),
                height = (size.height - bevelWidthPx).coerceAtLeast(0f)
            )

            // 2. Top-Left Bevel
            if (topLeftColor.alpha > 0f) {
                clipPath(topLeftClip) {
                    drawRoundRect(
                        color = topLeftColor,
                        topLeft = borderRectOffset,
                        size = borderRectSize,
                        cornerRadius = CornerRadius(innerRadius),
                        style = stroke
                    )
                }
            }

            // 3. Bottom-Right Bevel
            if (bottomRightColor.alpha > 0f) {
                clipPath(bottomRightClip) {
                    drawRoundRect(
                        color = bottomRightColor,
                        topLeft = borderRectOffset,
                        size = borderRectSize,
                        cornerRadius = CornerRadius(innerRadius),
                        style = stroke
                    )
                }
            }
        }
    }
}


