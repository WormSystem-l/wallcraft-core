package com.aesthetic.wallpapers.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * نقطة إعداد Retrofit الوحيدة في التطبيق.
 * BASE_URL مربوط برابط الباك اند الفعلي على Vercel.
 */
object RetrofitClient {

    private const val BASE_URL = "https://apinnxapi-api.vercel.app/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val apiService: WallpaperApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WallpaperApiService::class.java)
    }
}
