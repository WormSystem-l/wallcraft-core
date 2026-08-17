package com.aesthetic.wallpapers

import android.app.WallpaperManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aesthetic.wallpapers.data.model.Wallpaper
import com.aesthetic.wallpapers.data.repository.WallpaperRepository
import com.aesthetic.wallpapers.ui.screens.HomeScreen
import com.aesthetic.wallpapers.ui.screens.WallpaperDetailScreen
import com.aesthetic.wallpapers.ui.theme.AestheticWallpapersTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AestheticWallpapersTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color.Transparent) {
                    AppNavHost()
                }
            }
        }
    }
}

@androidx.compose.runtime.Composable
private fun AppNavHost() {
    val navController = rememberNavController()
    val repository = remember { WallpaperRepository() }
    // تخزين مؤقت بسيط: نحتفظ بآخر خلفية تم فتحها لتمريرها لشاشة التفاصيل بدون إعادة تحميل الشبكة
    var selectedWallpaper by remember { mutableStateOf<Wallpaper?>(null) }

    val context = androidx.compose.ui.platform.LocalContext.current

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                repository = repository,
                onWallpaperClick = { wallpaper ->
                    selectedWallpaper = wallpaper
                    navController.navigate("detail")
                }
            )
        }
        composable("detail") {
            selectedWallpaper?.let { wallpaper ->
                WallpaperDetailScreen(
                    wallpaper = wallpaper,
                    onBack = { navController.popBackStack() },
                    onApply = { wp ->
                        applyWallpaper(context, wp.imageUrl) { success ->
                            val msg = if (success) "تم تطبيق الخلفية بنجاح" else "حدث خطأ، حاول مرة أخرى"
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                        }
                    },
                    onDownload = {
                        Toast.makeText(context, "سيتم تفعيل التحميل عند ربط الـ backend", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}

/**
 * تطبيق الخلفية فعلياً على الجهاز عبر WallpaperManager.
 * ملاحظة: هذا يتطلب تحميل الصورة كـ Bitmap أولاً — مناسب فقط للـ MVP الحالي؛
 * لاحقاً يُفضّل استخدام Coil لتحميل الصورة بكفاءة أكبر وتفادي حجب الـ IO thread.
 */
private fun applyWallpaper(
    context: android.content.Context,
    imageUrl: String,
    onResult: (Boolean) -> Unit
) {
    CoroutineScope(Dispatchers.IO).launch {
        val success = try {
            val bitmap = withContext(Dispatchers.IO) {
                BitmapFactory.decodeStream(URL(imageUrl).openStream())
            }
            WallpaperManager.getInstance(context).setBitmap(bitmap)
            true
        } catch (e: Exception) {
            false
        }
        withContext(Dispatchers.Main) {
            onResult(success)
        }
    }
}
