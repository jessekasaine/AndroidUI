package com.example.androidui.ui.shared.button

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.androidui.ui.theme.AndroidUITheme
import com.example.androidui.ui.theme.Dimens

/**
 * A tactile 3D button featuring an inset highlight at the top and physical drop shadow rim at the bottom.
 * Sinks vertically on press with realistic collapsing depth and haptic feedback.
 */
@Composable
fun TactileButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    preset: ButtonPreset = ButtonPreset.Primary,
    size: ButtonSize = ButtonSize.Medium,
    colors: DepthButtonColors = DepthButtonDefaults.presetColors(preset),
    depth: Dp = when (size) {
        ButtonSize.Small -> DepthButtonDefaults.TactileDepthSmall
        ButtonSize.Large -> DepthButtonDefaults.TactileDepthLarge
        ButtonSize.Medium -> DepthButtonDefaults.TactileDepthDefault
    },
    cornerRadius: Dp = DepthButtonDefaults.cornerRadius(size),
    contentPadding: PaddingValues = DepthButtonDefaults.contentPadding(size),
    enableHaptics: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit
) {
    val isPressed by interactionSource.collectIsPressedAsState()
    val haptic = LocalHapticFeedback.current

    LaunchedEffect(isPressed) {
        if (isPressed && enabled && enableHaptics) {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
        }
    }

    // Depress the button down towards the base when pressed
    val targetOffset = if (isPressed && enabled) depth * 0.85f else 0.dp
    val animatedOffset by animateDpAsState(
        targetValue = targetOffset,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "TactileButtonOffset"
    )

    val currentContainerColor = colors.containerColor(enabled)
    val currentContentColor = colors.contentColor(enabled)
    val currentTopHighlight = colors.topHighlightColor(enabled)
    val currentBottomShadow = colors.bottomShadowColor(enabled)
    val shape = RoundedCornerShape(cornerRadius)

    // Outer box reserves vertical layout space for the 3D depth extrusion
    Box(
        modifier = modifier
            .padding(bottom = depth)
            .tactileShadow(
                depth = depth,
                currentOffset = animatedOffset,
                shadowColor = currentBottomShadow,
                cornerRadius = cornerRadius
            )
            .offset(y = animatedOffset)
            .clip(shape)
            .background(currentContainerColor)
            .topInnerHighlight(
                highlightColor = currentTopHighlight,
                cornerRadius = cornerRadius
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null, // pure tactile movement
                enabled = enabled,
                role = Role.Button,
                onClick = onClick
            )
            .semantics { role = Role.Button },
        contentAlignment = Alignment.Center
    ) {
        CompositionLocalProvider(
            LocalContentColor provides currentContentColor,
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
 * Text overload for [TactileButton].
 */
@Composable
fun TactileButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    preset: ButtonPreset = ButtonPreset.Primary,
    size: ButtonSize = ButtonSize.Medium,
    colors: DepthButtonColors = DepthButtonDefaults.presetColors(preset),
    depth: Dp = when (size) {
        ButtonSize.Small -> DepthButtonDefaults.TactileDepthSmall
        ButtonSize.Large -> DepthButtonDefaults.TactileDepthLarge
        ButtonSize.Medium -> DepthButtonDefaults.TactileDepthDefault
    },
    cornerRadius: Dp = DepthButtonDefaults.cornerRadius(size),
    contentPadding: PaddingValues = DepthButtonDefaults.contentPadding(size),
    enableHaptics: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    TactileButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        preset = preset,
        size = size,
        colors = colors,
        depth = depth,
        cornerRadius = cornerRadius,
        contentPadding = contentPadding,
        enableHaptics = enableHaptics,
        interactionSource = interactionSource
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.SemiBold
        )
    }
}

/**
 * An embossed depth button featuring a soft top inner highlight and bottom drop shadow.
 * Switches into a sunken inverted bevel state when pressed, with ripple feedback.
 */
@Composable
fun EmbossedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    preset: ButtonPreset = ButtonPreset.Primary,
    size: ButtonSize = ButtonSize.Medium,
    colors: DepthButtonColors = DepthButtonDefaults.presetColors(preset),
    depth: Dp = DepthButtonDefaults.EmbossedDepthDefault,
    cornerRadius: Dp = DepthButtonDefaults.cornerRadius(size),
    contentPadding: PaddingValues = DepthButtonDefaults.contentPadding(size),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit
) {
    val isPressed by interactionSource.collectIsPressedAsState()

    val currentContainerColor = colors.containerColor(enabled)
    val currentContentColor = colors.contentColor(enabled)
    val currentTopHighlight = colors.topHighlightColor(enabled)
    val currentBottomShadow = colors.bottomShadowColor(enabled)
    val shape = RoundedCornerShape(cornerRadius)

    Box(
        modifier = modifier
            .padding(bottom = depth)
            .embossedDepth(
                isPressed = isPressed && enabled,
                highlightColor = currentTopHighlight,
                shadowColor = currentBottomShadow,
                cornerRadius = cornerRadius,
                depth = depth
            )
            .clip(shape)
            .background(currentContainerColor)
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(),
                enabled = enabled,
                role = Role.Button,
                onClick = onClick
            )
            .semantics { role = Role.Button },
        contentAlignment = Alignment.Center
    ) {
        CompositionLocalProvider(
            LocalContentColor provides currentContentColor,
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
    depth: Dp = DepthButtonDefaults.EmbossedDepthDefault,
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

@Preview(name = "Tactile & Embossed Buttons - Light Mode", showBackground = true)
@Composable
fun DepthButtonsPreviewLight() {
    AndroidUITheme(darkTheme = false) {
        Surface {
            DepthButtonsShowcase()
        }
    }
}

@Preview(name = "Tactile & Embossed Buttons - Dark Mode", showBackground = true)
@Composable
fun DepthButtonsPreviewDark() {
    AndroidUITheme(darkTheme = true) {
        Surface {
            DepthButtonsShowcase()
        }
    }
}

@Composable
fun DepthButtonsShowcase() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Tactile 3D Buttons (Sink on Press)",
            style = LocalTextStyle.current.copy(fontWeight = FontWeight.Bold)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TactileButton(
                text = "Primary",
                onClick = {},
                preset = ButtonPreset.Primary,
                size = ButtonSize.Medium,
                modifier = Modifier.weight(1f)
            )
            TactileButton(
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
            TactileButton(
                text = "Surface",
                onClick = {},
                preset = ButtonPreset.Surface,
                size = ButtonSize.Medium,
                modifier = Modifier.weight(1f)
            )
            TactileButton(
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
            TactileButton(
                text = "Small",
                onClick = {},
                size = ButtonSize.Small
            )
            TactileButton(
                text = "Large Tactile",
                onClick = {},
                size = ButtonSize.Large
            )
            TactileButton(
                text = "Disabled",
                onClick = {},
                enabled = false
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Embossed Buttons (Bevel & Ripple)",
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
    }
}
