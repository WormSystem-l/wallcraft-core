package com.aesthetic.wallpapers.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aesthetic.wallpapers.data.model.Wallpaper
import com.aesthetic.wallpapers.ui.components.GlassCard
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze

/**
 * شاشة معاينة الخلفية بالحجم الكامل. الأزرار السفلية (تطبيق / تحميل) زجاجية عائمة
 * فوق الصورة نفسها، فيظهر تأثير الـ Blur بأوضح صورة لأن المحتوى تحتها صورة حقيقية بألوان متغيرة.
 */
@Composable
fun WallpaperDetailScreen(
    wallpaper: Wallpaper,
    onBack: () -> Unit,
    onApply: (Wallpaper) -> Unit,
    onDownload: (Wallpaper) -> Unit
) {
    val hazeState = remember { HazeState() }

    Box(modifier = Modifier.fillMaxSize()) {
        AsyncImage(
            model = wallpaper.imageUrl,
            contentDescription = wallpaper.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .haze(state = hazeState)
        )

        // زر الرجوع الزجاجي — أعلى اليسار
        GlassCard(
            hazeState = hazeState,
            cornerRadius = 50.dp,
            modifier = Modifier
                .statusBarsPadding()
                .padding(20.dp)
                .size(46.dp)
                .align(Alignment.TopStart)
                .clickable(onClick = onBack)
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }

        // شريط الأزرار الزجاجي السفلي: تطبيق كخلفية + تحميل
        GlassCard(
            hazeState = hazeState,
            cornerRadius = 28.dp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 18.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = wallpaper.title, style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = wallpaper.category.displayName,
                        style = MaterialTheme.typography.labelSmall
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    ActionCircle(
                        icon = Icons.Filled.Download,
                        contentDescription = "Download",
                        onClick = { onDownload(wallpaper) }
                    )
                    ActionCircle(
                        icon = Icons.Filled.CheckCircle,
                        contentDescription = "Apply",
                        highlighted = true,
                        onClick = { onApply(wallpaper) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ActionCircle(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    contentDescription: String,
    highlighted: Boolean = false,
    onClick: () -> Unit
) {
    val bg = if (highlighted) Color(0xFF4FACFE) else Color.White.copy(alpha = 0.15f)
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(bg)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(imageVector = icon, contentDescription = contentDescription, tint = Color.White)
    }
}

