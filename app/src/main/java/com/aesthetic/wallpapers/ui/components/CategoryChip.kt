package com.aesthetic.wallpapers.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aesthetic.wallpapers.ui.theme.CosmicPurple
import com.aesthetic.wallpapers.ui.theme.ElectricBlue
import com.aesthetic.wallpapers.ui.theme.GlassBorder
import com.aesthetic.wallpapers.ui.theme.GlassSurfaceMedium
import com.aesthetic.wallpapers.ui.theme.TextPrimary
import com.aesthetic.wallpapers.ui.theme.TextSecondary

@Composable
fun CategoryChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) CosmicPurple.copy(alpha = 0.35f) else GlassSurfaceMedium,
        label = "chipBackground"
    )
    val borderColor = if (isSelected) ElectricBlue.copy(alpha = 0.6f) else GlassBorder
    val textColor = if (isSelected) TextPrimary else TextSecondary

    Text(
        text = label,
        color = textColor,
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(backgroundColor)
            .border(BorderStroke(1.dp, borderColor), RoundedCornerShape(50))
            .clickable(onClick = onClick)
            .padding(horizontal = 18.dp, vertical = 10.dp)
    )
}
