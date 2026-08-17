package com.aesthetic.wallpapers.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items as gridItems
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.aesthetic.wallpapers.data.model.Wallpaper
import com.aesthetic.wallpapers.data.model.WallpaperCategory
import com.aesthetic.wallpapers.data.repository.WallpaperRepository
import com.aesthetic.wallpapers.ui.components.CategoryChip
import com.aesthetic.wallpapers.ui.components.WallpaperCard
import com.aesthetic.wallpapers.ui.theme.DeepSpaceBlack
import com.aesthetic.wallpapers.ui.theme.ElectricBlue
import com.aesthetic.wallpapers.ui.theme.MidnightNavy
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild

/**
 * الشاشة الرئيسية: خلفية متدرجة عميقة + شبكة خلفيات + شريط تصنيفات زجاجي عائم أعلى الشاشة.
 * الـ HazeState هنا هو ما يربط بين المحتوى (اللي بيتحرك تحت) والعناصر الزجاجية (اللي بتعمل لها Blur).
 */
@Composable
fun HomeScreen(
    onWallpaperClick: (Wallpaper) -> Unit,
    repository: WallpaperRepository = remember { WallpaperRepository() }
) {
    val hazeState = remember { HazeState() }
    var wallpapers by remember { mutableStateOf<List<Wallpaper>>(emptyList()) }
    var selectedCategory by remember { mutableStateOf(WallpaperCategory.ALL) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(selectedCategory) {
        isLoading = true
        wallpapers = repository.getWallpapersByCategory(selectedCategory)
        isLoading = false
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(DeepSpaceBlack, MidnightNavy, DeepSpaceBlack)
                )
            )
    ) {
        // المحتوى القابل للتمرير — كل ما يمر تحت الشريط الزجاجي العلوي يتم عمل Blur له
        Column(
            modifier = Modifier
                .fillMaxSize()
                .haze(state = hazeState)
        ) {
            Spacer_TopBarHeight()

            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = ElectricBlue)
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    gridItems(wallpapers, key = { it.id }) { wallpaper ->
                        WallpaperCard(
                            wallpaper = wallpaper,
                            onClick = { onWallpaperClick(wallpaper) }
                        )
                    }
                }
            }
        }

        // الشريط العلوي الزجاجي العائم: خلفيته Blur حقيقي (haze.child) للمحتوى المتحرك تحته
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .hazeChild(state = hazeState)
                .background(DeepSpaceBlack.copy(alpha = 0.25f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
            ) {
                Text(
                    text = "Wallpapers",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
                )

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    items(WallpaperCategory.entries) { category ->
                        CategoryChip(
                            label = category.displayName,
                            isSelected = category == selectedCategory,
                            onClick = { selectedCategory = category }
                        )
                    }
                }
            }
        }
    }
}

/** مساحة فارغة تعادل ارتفاع الشريط العلوي حتى لا تختفي أول صفوف الشبكة تحته */
@Composable
private fun Spacer_TopBarHeight() {
    Box(modifier = Modifier.height(128.dp))
}
