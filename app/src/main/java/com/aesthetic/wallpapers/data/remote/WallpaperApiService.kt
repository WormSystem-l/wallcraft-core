package com.aesthetic.wallpapers.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * استجابة /api/wallpapers من الباك اند (Vercel).
 * الحقول هنا مطابقة تماماً لما بيرجعه api/wallpapers.js.
 */
data class WallpaperResponseDto(
    val wallpapers: List<WallpaperDto>,
    val count: Int
)

data class WallpaperDto(
    val id: String,
    val title: String,
    val category: String,
    val imageUrl: String,
    val isTrending: Boolean = false,
    val createdAt: String? = null
)

interface WallpaperApiService {
    @GET("api/wallpapers")
    suspend fun getWallpapers(
        @Query("category") category: String? = null
    ): WallpaperResponseDto
}
