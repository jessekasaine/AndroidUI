package com.example.androidui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidui.ui.shared.button.ButtonPreset
import com.example.androidui.ui.shared.button.ButtonSize
import com.example.androidui.ui.shared.button.DepthButtonDefaults
import com.example.androidui.ui.shared.button.EmbossedButton
import com.example.androidui.ui.theme.AndroidUITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidUITheme {
                EmbossedButtonDemoScreen()
            }
        }
    }
}

@Composable
fun EmbossedButtonDemoScreen() {
    var clickCount by remember { mutableIntStateOf(0) }
    var lastAction by remember { mutableStateOf("Tap any embossed button to test depth") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Header
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Embossed Depth Buttons",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )
                Text(
                    text = "Bevel depth with top inset highlight and bottom drop shadow. Inverts bevel when pressed.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }

            // Live Action Feedback Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Last Action",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = MaterialTheme.colorScheme.outline
                            )
                        )
                        Text(
                            text = lastAction,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Text(
                            text = "Clicks: $clickCount",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                    }
                }
            }

            // Section 1: Presets
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Text(
                    text = "1. Color Presets",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )

                // Presets: Primary & Secondary
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    EmbossedButton(
                        onClick = {
                            clickCount++
                            lastAction = "Embossed Primary Pressed"
                        },
                        preset = ButtonPreset.Primary,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "✓", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Primary", fontWeight = FontWeight.SemiBold)
                    }

                    EmbossedButton(
                        onClick = {
                            clickCount++
                            lastAction = "Embossed Secondary Pressed"
                        },
                        preset = ButtonPreset.Secondary,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "★", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Secondary", fontWeight = FontWeight.SemiBold)
                    }
                }

                // Presets: Surface & Destructive
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    EmbossedButton(
                        onClick = {
                            clickCount++
                            lastAction = "Embossed Surface Pressed"
                        },
                        preset = ButtonPreset.Surface,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "♥", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Surface", fontWeight = FontWeight.SemiBold)
                    }

                    EmbossedButton(
                        onClick = {
                            clickCount++
                            lastAction = "Embossed Destructive Pressed"
                        },
                        preset = ButtonPreset.Destructive,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "✕", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Delete", fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            // Section 2: Size & State Variants
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Text(
                    text = "2. Sizes & Disabled State",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    EmbossedButton(
                        text = "Small",
                        onClick = {
                            clickCount++
                            lastAction = "Small Embossed Pressed"
                        },
                        size = ButtonSize.Small,
                        preset = ButtonPreset.Primary
                    )

                    EmbossedButton(
                        text = "Medium",
                        onClick = {
                            clickCount++
                            lastAction = "Medium Embossed Pressed"
                        },
                        size = ButtonSize.Medium,
                        preset = ButtonPreset.Secondary,
                        modifier = Modifier.weight(1f)
                    )

                    EmbossedButton(
                        text = "Disabled",
                        onClick = {},
                        enabled = false,
                        size = ButtonSize.Medium
                    )
                }

                // Large Full Width Embossed Button
                EmbossedButton(
                    onClick = {
                        clickCount++
                        lastAction = "Large Embossed Action Pressed"
                    },
                    preset = ButtonPreset.Primary,
                    size = ButtonSize.Large,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "＋", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Create New Project (Large Embossed)",
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Section 3: Custom Style Token
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Text(
                    text = "3. Custom DepthButtonStyle Token",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                )

                // Custom Style Override (e.g. Glowing Neon Amber Bevel)
                val customStyle = DepthButtonDefaults.style(
                    preset = ButtonPreset.Secondary,
                    size = ButtonSize.Large
                ).copy(
                    depth = 4.dp,
                    cornerRadius = 24.dp
                )

                EmbossedButton(
                    onClick = {
                        clickCount++
                        lastAction = "Custom DepthButtonStyle Pressed"
                    },
                    style = customStyle,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "⚡", fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Custom Style Token Button (4dp depth, 24dp radius)",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmbossedButtonDemoScreenPreview() {
    AndroidUITheme {
        EmbossedButtonDemoScreen()
    }
}