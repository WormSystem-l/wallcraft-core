package com.aesthetic.wallpapers.data.model

/**
 * نموذج الخلفية — نفس الشكل المتوقع من الـ backend (Vercel) لاحقاً.
 * حالياً بيانات تجريبية (Mock) لحد ما يتم ربط الـ API الحقيقي.
 */
data class Wallpaper(
    val id: String,
    val title: String,
    val imageUrl: String,
    val thumbnailUrl: String = imageUrl,
    val category: WallpaperCategory,
    val isTrending: Boolean = false,
    val width: Int = 1170,
    val height: Int = 2532
)

enum class WallpaperCategory(val displayName: String) {
    ALL("All"),
    MINIMALIST("Minimalist"),
    ABSTRACT("Abstract"),
    NATURE("Nature"),
    DARK_MODE("Dark Mode"),
    DEPTH_EFFECT("Depth Effect")
}
