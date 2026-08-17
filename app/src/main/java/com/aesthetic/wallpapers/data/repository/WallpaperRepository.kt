package com.aesthetic.wallpapers.data.repository

import com.aesthetic.wallpapers.data.model.Wallpaper
import com.aesthetic.wallpapers.data.model.WallpaperCategory
import com.aesthetic.wallpapers.data.remote.RetrofitClient
import com.aesthetic.wallpapers.data.remote.WallpaperDto
import kotlinx.coroutines.delay

/**
 * Repository المسؤول عن جلب الخلفيات.
 * يحاول أولاً النداء على الباك اند الحقيقي (Vercel)، ولو فشل الاتصال
 * (مثلاً BASE_URL لسه مش متظبط، أو مفيش إنترنت) يرجع لبيانات Mock تجريبية
 * حتى لا تتوقف واجهة التطبيق عن العمل أثناء التطوير.
 */
class WallpaperRepository {

    suspend fun getWallpapers(): List<Wallpaper> {
        return fetchFromApi(category = null)
    }

    suspend fun getWallpapersByCategory(category: WallpaperCategory): List<Wallpaper> {
        val categoryParam = if (category == WallpaperCategory.ALL) null else category.displayName
        return fetchFromApi(categoryParam)
    }

    private suspend fun fetchFromApi(category: String?): List<Wallpaper> {
        return try {
            val response = RetrofitClient.apiService.getWallpapers(category)
            response.wallpapers.map { it.toDomain() }
        } catch (e: Exception) {
            // فشل الاتصال بالباك اند الحقيقي (رابط غير مضبوط بعد / لا يوجد إنترنت) → استخدم بيانات تجريبية
            delay(300)
            val fallback = mockWallpapers
            if (category == null) fallback
            else fallback.filter { it.category.displayName == category }
        }
    }

    private fun WallpaperDto.toDomain(): Wallpaper {
        val matchedCategory = WallpaperCategory.entries.find { it.displayName == category }
            ?: WallpaperCategory.ABSTRACT
        return Wallpaper(
            id = id,
            title = title,
            imageUrl = imageUrl,
            category = matchedCategory,
            isTrending = isTrending
        )
    }

    companion object {
        // بيانات احتياطية (Fallback) تظهر فقط لو تعذر الوصول للباك اند الحقيقي
        val mockWallpapers = listOf(
            Wallpaper(
                id = "1",
                title = "Gradient Dreams",
                imageUrl = "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?w=800",
                category = WallpaperCategory.ABSTRACT,
                isTrending = true
            ),
            Wallpaper(
                id = "2",
                title = "Mountain Mist",
                imageUrl = "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=800",
                category = WallpaperCategory.NATURE,
                isTrending = true
            ),
            Wallpaper(
                id = "3",
                title = "Pure Minimal",
                imageUrl = "https://images.unsplash.com/photo-1557683316-973673baf926?w=800",
                category = WallpaperCategory.MINIMALIST
            ),
            Wallpaper(
                id = "4",
                title = "Midnight Depth",
                imageUrl = "https://images.unsplash.com/photo-1534796636912-3b95b3ab5986?w=800",
                category = WallpaperCategory.DARK_MODE,
                isTrending = true
            ),
            Wallpaper(
                id = "5",
                title = "Floating Layers",
                imageUrl = "https://images.unsplash.com/photo-1620121692029-d088224ddc74?w=800",
                category = WallpaperCategory.DEPTH_EFFECT
            ),
            Wallpaper(
                id = "6",
                title = "Ocean Waves",
                imageUrl = "https://images.unsplash.com/photo-1505142468610-359e7d316be0?w=800",
                category = WallpaperCategory.NATURE
            ),
            Wallpaper(
                id = "7",
                title = "Neon Abstract",
                imageUrl = "https://images.unsplash.com/photo-1620641622064-5a1d6e0f8fdd?w=800",
                category = WallpaperCategory.ABSTRACT
            ),
            Wallpaper(
                id = "8",
                title = "Clean Slate",
                imageUrl = "https://images.unsplash.com/photo-1618172193622-ae2d025f4032?w=800",
                category = WallpaperCategory.MINIMALIST
            ),
        )
    }
}
