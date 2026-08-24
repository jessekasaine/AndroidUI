package com.example.androidui.ui.shared.button

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
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
    style: DepthButtonStyle = DepthButtonDefaults.style(preset = preset, size = size),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit
) {
    val isPressed by interactionSource.collectIsPressedAsState()

    val currentContainerColor = style.colors.containerColor(enabled)
    val currentContentColor = style.colors.contentColor(enabled)
    val shape = RoundedCornerShape(style.cornerRadius)

    Box(
        modifier = modifier
            .padding(bottom = style.depth)
            .embossedDepth(
                shape = shape,
                shadowStyle = style.shadowStyle,
                isPressed = isPressed && enabled,
                enabled = enabled
            )
            .clip(shape)
            .background(currentContainerColor)
            .embossedInnerDepth(
                shape = shape,
                shadowStyle = style.shadowStyle,
                isPressed = isPressed && enabled,
                enabled = enabled
            )
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
            LocalTextStyle provides style.textStyle
        ) {
            Row(
                modifier = Modifier
                    .defaultMinSize(minHeight = DepthButtonDefaults.minHeight(size))
                    .padding(style.contentPadding),
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
    style: DepthButtonStyle = DepthButtonDefaults.style(preset = preset, size = size),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    EmbossedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        preset = preset,
        size = size,
        style = style,
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
            text = "Embossed Buttons (Top Highlight + Bottom Shadow)",
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
