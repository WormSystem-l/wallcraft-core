package com.aesthetic.wallpapers.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.aesthetic.wallpapers.ui.theme.GlassBorder
import com.aesthetic.wallpapers.ui.theme.GlassSurfaceLight
import com.aesthetic.wallpapers.ui.theme.GlassSurfaceMedium
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild

/**
 * بطاقة زجاجية (Glass Card) بتأثير Blur حقيقي عبر مكتبة Haze.
 * تُستخدم كأساس لكل العناصر العائمة في التطبيق: الـ Navigation bar، بطاقات التصنيفات، الأزرار العائمة.
 *
 * @param hazeState الحالة المشتركة من الشاشة الأب (المحتوى اللي هيتم عمل Blur له خلف الكارد)
 */
@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 24.dp,
    hazeState: HazeState? = null,
    content: @Composable () -> Unit
) {
    val shape = RoundedCornerShape(cornerRadius)

    Box(
        modifier = modifier
            .clip(shape)
            .then(
                // لو فيه HazeState متاح (من الشاشة الأب) → Blur حقيقي للمحتوى اللي وراه
                // لو مفيش (مثلاً فوق خلفية ساكنة) → نكتفي بخلفية شبه شفافة
                if (hazeState != null) {
                    Modifier.hazeChild(state = hazeState, shape = shape)
                } else {
                    Modifier.background(GlassSurfaceMedium)
                }
            )
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(GlassSurfaceLight, GlassSurfaceMedium)
                )
            )
            .border(BorderStroke(1.dp, GlassBorder), shape),
    ) {
        content()
    }
}

/** حدود زجاجية خفيفة تُضاف فوق أي عنصر Glass لإبراز الحواف بشكل واقعي */
fun glassBorder(width: Dp = 1.dp) = BorderStroke(width, GlassBorder)
