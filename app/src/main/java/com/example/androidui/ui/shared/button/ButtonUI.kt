package com.example.androidui.ui.shared.button

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.androidui.ui.theme.AndroidUITheme

/**
 * A modern subtle neumorphic/embossed surface button featuring:
 * - Soft underlying physical depth shadow that dynamically compresses when pressed
 * - Directional light highlight gradient (top-left to bottom-right)
 * - Directional shadow gradient (bottom-right to top-left)
 * - Tactile downward Y-offset animation upon user press
 */
@Composable
fun EmbossedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    preset: ButtonPreset = ButtonPreset.Primary,
    size: ButtonSize = ButtonSize.Medium,
    colors: DepthButtonColors = DepthButtonDefaults.presetColors(preset),
    depth: Dp = DepthButtonDefaults.depth(size),
    pressTravel: Dp = DepthButtonDefaults.pressTravel(size),
    cornerRadius: Dp = DepthButtonDefaults.cornerRadius(size),
    contentPadding: PaddingValues = DepthButtonDefaults.contentPadding(size),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit
) {
    val isPressed by interactionSource.collectIsPressedAsState()

    val currentOffsetY by animateDpAsState(
        targetValue = if (isPressed && enabled) pressTravel else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "embossedButtonOffset"
    )

    val surfaceColor = colors.surfaceColor(enabled)
    val contentColor = colors.contentColor(enabled)
    val highlightColor = colors.highlightColor(enabled)
    val shadowGradientColor = colors.shadowGradientColor(enabled)
    val depthShadowColor = colors.depthShadowColor(enabled)
    val shape = RoundedCornerShape(cornerRadius)

    Box(
        modifier = modifier
            .padding(bottom = depth)
            .offset(y = currentOffsetY)
            .subtleEmbossedSurface(
                surfaceColor = surfaceColor,
                highlightColor = highlightColor,
                shadowGradientColor = shadowGradientColor,
                depthShadowColor = depthShadowColor,
                cornerRadius = cornerRadius,
                depth = depth,
                currentOffsetY = currentOffsetY
            )
            .clip(shape)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                role = Role.Button,
                onClick = onClick
            )
            .semantics { role = Role.Button },
        contentAlignment = Alignment.Center
    ) {
        CompositionLocalProvider(
            LocalContentColor provides contentColor,
            LocalTextStyle provides DepthButtonDefaults.textStyle(size)
        ) {
            Row(
                modifier = Modifier
                    .defaultMinSize(minHeight = DepthButtonDefaults.minHeight(size))
                    .padding(contentPadding),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                content()
            }
        }
    }
}

/**
 * Text overload for [EmbossedButton].
 */
@Composable
fun EmbossedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    preset: ButtonPreset = ButtonPreset.Primary,
    size: ButtonSize = ButtonSize.Medium,
    colors: DepthButtonColors = DepthButtonDefaults.presetColors(preset),
    depth: Dp = DepthButtonDefaults.depth(size),
    pressTravel: Dp = DepthButtonDefaults.pressTravel(size),
    cornerRadius: Dp = DepthButtonDefaults.cornerRadius(size),
    contentPadding: PaddingValues = DepthButtonDefaults.contentPadding(size),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    EmbossedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        preset = preset,
        size = size,
        colors = colors,
        depth = depth,
        pressTravel = pressTravel,
        cornerRadius = cornerRadius,
        contentPadding = contentPadding,
        interactionSource = interactionSource
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview(name = "Embossed Buttons - Light Mode", showBackground = true)
@Composable
fun EmbossedButtonsPreviewLight() {
    AndroidUITheme(darkTheme = false) {
        Surface {
            EmbossedButtonsShowcase()
        }
    }
}

@Preview(name = "Embossed Buttons - Dark Mode", showBackground = true)
@Composable
fun EmbossedButtonsPreviewDark() {
    AndroidUITheme(darkTheme = true) {
        Surface {
            EmbossedButtonsShowcase()
        }
    }
}

@Composable
fun EmbossedButtonsShowcase() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Modern Subtle Embossed / Neumorphic Buttons",
            style = LocalTextStyle.current.copy(fontWeight = FontWeight.Bold)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            EmbossedButton(
                text = "Primary",
                onClick = {},
                preset = ButtonPreset.Primary,
                size = ButtonSize.Medium,
                modifier = Modifier.weight(1f)
            )
            EmbossedButton(
                text = "Secondary",
                onClick = {},
                preset = ButtonPreset.Secondary,
                size = ButtonSize.Medium,
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            EmbossedButton(
                text = "Surface",
                onClick = {},
                preset = ButtonPreset.Surface,
                size = ButtonSize.Medium,
                modifier = Modifier.weight(1f)
            )
            EmbossedButton(
                text = "Destructive",
                onClick = {},
                preset = ButtonPreset.Destructive,
                size = ButtonSize.Medium,
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            EmbossedButton(
                text = "Small",
                onClick = {},
                size = ButtonSize.Small
            )
            EmbossedButton(
                text = "Large Embossed",
                onClick = {},
                size = ButtonSize.Large
            )
            EmbossedButton(
                text = "Disabled",
                onClick = {},
                enabled = false
            )
        }
    }
}

