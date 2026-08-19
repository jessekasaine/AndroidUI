package com.example.androidui.ui.shared.button

import android.graphics.BlurMaskFilter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Draws a physical 3D extrusion base and soft ambient drop shadow behind a tactile button.
 * As the button depresses, [currentOffset] increases toward [depth], collapsing the visible base.
 */
fun Modifier.tactileShadow(
    depth: Dp,
    currentOffset: Dp,
    shadowColor: Color,
    cornerRadius: Dp,
    ambientShadowAlpha: Float = 0.30f
): Modifier = this.drawBehind {
    val cornerRadiusPx = cornerRadius.toPx()
    val depthPx = depth.toPx()
    val offsetPx = currentOffset.toPx()

    // 1. Soft Ambient Drop Shadow underneath the base extrusion
    if (depthPx > 0f) {
        val ambientBlur = 5.dp.toPx()
        val ambientOffsetY = depthPx + 1.5.dp.toPx()
        drawIntoCanvas { canvas ->
            val paint = Paint().apply {
                this.color = shadowColor.copy(alpha = shadowColor.alpha * ambientShadowAlpha)
                asFrameworkPaint().maskFilter =
                    BlurMaskFilter(ambientBlur.coerceAtLeast(1f), BlurMaskFilter.Blur.NORMAL)
            }
            canvas.drawRoundRect(
                left = 2.dp.toPx(),
                top = ambientOffsetY,
                right = size.width - 2.dp.toPx(),
                bottom = size.height + ambientOffsetY - 2.dp.toPx(),
                radiusX = cornerRadiusPx,
                radiusY = cornerRadiusPx,
                paint = paint
            )
        }
    }

    // 2. Physical 3D Extruded Base Rim
    if (depthPx > 0f) {
        drawRoundRect(
            color = shadowColor,
            topLeft = Offset(0f, offsetPx.coerceAtMost(depthPx)),
            size = Size(size.width, size.height),
            cornerRadius = CornerRadius(cornerRadiusPx, cornerRadiusPx)
        )
    }
}

/**
 * Draws an inner highlight line/stroke along the top perimeter of the shape.
 */
fun Modifier.topInnerHighlight(
    highlightColor: Color,
    cornerRadius: Dp,
    strokeWidth: Dp = 1.5.dp
): Modifier = this.drawWithContent {
    drawContent()
    val strokePx = strokeWidth.toPx()
    val halfStroke = strokePx / 2f
    val cornerRadiusPx = cornerRadius.toPx()

    val highlightBrush = Brush.verticalGradient(
        0.0f to highlightColor,
        0.30f to highlightColor.copy(alpha = highlightColor.alpha * 0.45f),
        0.65f to Color.Transparent,
        startY = 0f,
        endY = size.height
    )

    drawRoundRect(
        brush = highlightBrush,
        topLeft = Offset(halfStroke, halfStroke),
        size = Size(size.width - strokePx, size.height - strokePx),
        cornerRadius = CornerRadius(
            (cornerRadiusPx - halfStroke).coerceAtLeast(0f),
            (cornerRadiusPx - halfStroke).coerceAtLeast(0f)
        ),
        style = Stroke(width = strokePx)
    )
}

/**
 * Draws an embossed dual-tone depth border with top highlight and bottom drop shadow.
 * Inverts the shading gradient when [isPressed] is true for a sunken/pressed effect.
 */
fun Modifier.embossedDepth(
    isPressed: Boolean,
    highlightColor: Color,
    shadowColor: Color,
    cornerRadius: Dp,
    depth: Dp = 2.dp,
    strokeWidth: Dp = 1.5.dp
): Modifier = this
    .drawBehind {
        // Soft outer drop shadow when not pressed
        if (!isPressed && depth > 0.dp) {
            val blur = 4.dp.toPx()
            drawIntoCanvas { canvas ->
                val paint = Paint().apply {
                    this.color = shadowColor.copy(alpha = shadowColor.alpha * 0.35f)
                    asFrameworkPaint().maskFilter =
                        BlurMaskFilter(blur.coerceAtLeast(1f), BlurMaskFilter.Blur.NORMAL)
                }
                canvas.drawRoundRect(
                    left = 1.dp.toPx(),
                    top = depth.toPx(),
                    right = size.width - 1.dp.toPx(),
                    bottom = size.height + depth.toPx(),
                    radiusX = cornerRadius.toPx(),
                    radiusY = cornerRadius.toPx(),
                    paint = paint
                )
            }
        }
    }
    .drawWithContent {
        drawContent()

        val strokePx = strokeWidth.toPx()
        val halfStroke = strokePx / 2f
        val cornerRadiusPx = cornerRadius.toPx()

        // Dual-tone inner bevel
        val borderBrush = if (!isPressed) {
            Brush.verticalGradient(
                0.0f to highlightColor,
                0.35f to highlightColor.copy(alpha = highlightColor.alpha * 0.35f),
                0.60f to Color.Transparent,
                1.0f to shadowColor.copy(alpha = shadowColor.alpha * 0.35f),
                startY = 0f,
                endY = size.height
            )
        } else {
            // Sunken inverted bevel
            Brush.verticalGradient(
                0.0f to shadowColor.copy(alpha = shadowColor.alpha * 0.50f),
                0.35f to shadowColor.copy(alpha = shadowColor.alpha * 0.20f),
                0.65f to Color.Transparent,
                1.0f to highlightColor.copy(alpha = highlightColor.alpha * 0.35f),
                startY = 0f,
                endY = size.height
            )
        }

        drawRoundRect(
            brush = borderBrush,
            topLeft = Offset(halfStroke, halfStroke),
            size = Size(size.width - strokePx, size.height - strokePx),
            cornerRadius = CornerRadius(
                (cornerRadiusPx - halfStroke).coerceAtLeast(0f),
                (cornerRadiusPx - halfStroke).coerceAtLeast(0f)
            ),
            style = Stroke(width = strokePx)
        )
    }
