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
import androidx.compose.ui.unit.dp

/**
 * Draws a physical gradient-free and animation-free embossed surface with:
 * - Idle / Normal: Solid under-button depth shadow layer drawn at offset (depth, depth) underneath the surface.
 * - Pressed: Instant physical press (under-shadow is flattened/hidden) + top-left interior sunken deboss shadow.
 * - Disabled: Retains a subtle 1.dp muted under-shadow layer.
 */
fun Modifier.embossedSurface(
    isPressed: Boolean,
    enabled: Boolean,
    surfaceColor: Color,
    shadowColor: Color,
    cornerRadius: Dp,
    depth: Dp
): Modifier = this.drawWithCache {
    val cornerRadiusPx = cornerRadius.toPx()
    val depthPx = depth.toPx()
    val disabledDepthPx = 1.dp.toPx()

    // Interior sunken clip and stroke for pressed (debossed) state
    val debossStrokeWidth = (depthPx * 1.2f).coerceAtLeast(2.dp.toPx())
    val debossInset = debossStrokeWidth / 2f
    val debossInnerRadius = (cornerRadiusPx - debossInset).coerceAtLeast(0f)
    val debossStroke = Stroke(width = debossStrokeWidth)

    val topLeftClip = Path().apply {
        moveTo(0f, 0f)
        lineTo(size.width, 0f)
        lineTo(0f, size.height)
        close()
    }

    onDrawBehind {
        if (!enabled) {
            // Disabled State: subtle 1.dp muted under-shadow + disabled surface
            drawRoundRect(
                color = shadowColor,
                topLeft = Offset(disabledDepthPx, disabledDepthPx),
                size = size,
                cornerRadius = CornerRadius(cornerRadiusPx)
            )
            drawRoundRect(
                color = surfaceColor,
                size = size,
                cornerRadius = CornerRadius(cornerRadiusPx)
            )
        } else if (isPressed) {
            // Pressed (Debossed) State:
            // The under-button shadow is hidden (surface pressed into surface plane)
            // 1. Main surface
            drawRoundRect(
                color = surfaceColor,
                size = size,
                cornerRadius = CornerRadius(cornerRadiusPx)
            )

            // 2. Interior sunken (debossed) top-left shadow
            if (shadowColor.alpha > 0f) {
                clipPath(topLeftClip) {
                    drawRoundRect(
                        color = shadowColor,
                        topLeft = Offset(debossInset, debossInset),
                        size = Size(
                            width = (size.width - debossStrokeWidth).coerceAtLeast(0f),
                            height = (size.height - debossStrokeWidth).coerceAtLeast(0f)
                        ),
                        cornerRadius = CornerRadius(debossInnerRadius),
                        style = debossStroke
                    )
                }
            }
        } else {
            // Idle (Embossed) State:
            // 1. Solid under-button depth shadow
            drawRoundRect(
                color = shadowColor,
                topLeft = Offset(depthPx, depthPx),
                size = size,
                cornerRadius = CornerRadius(cornerRadiusPx)
            )

            // 2. Main surface
            drawRoundRect(
                color = surfaceColor,
                size = size,
                cornerRadius = CornerRadius(cornerRadiusPx)
            )
        }
    }
}


