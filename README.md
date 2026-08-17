# 🌌 Aesthetic Wallpapers

تطبيق أندرويد لعرض وتطبيق خلفيات بتصميم **Glassmorphism** (زجاج + Blur) مستوحى من واجهة iOS، مبني بـ **Kotlin + Jetpack Compose**.

هذه هي **المرحلة الأولى** فقط من الخطة الكاملة: تطبيق خلفيات مع كتالوجات وتصنيفات ومعاينة، بدون شاشة قفل مخصصة أو خدمات الإشعارات بعد.

---

## ✨ المزايا الحالية

- شبكة خلفيات (Grid) بتصميم بطاقات زجاجية
- شريط علوي عائم بتأثير **Blur حقيقي** (مكتبة [Haze](https://github.com/chrisbanes/haze))
- تصنيفات: Minimalist, Abstract, Nature, Dark Mode, Depth Effect
- شاشة معاينة كاملة الحجم + زر "تطبيق كخلفية" فعلي عبر `WallpaperManager`
- بيانات تجريبية (Mock) جاهزة للاستبدال بـ API حقيقي لاحقاً

## 🧱 البنية التقنية

```
app/src/main/java/com/aesthetic/wallpapers/
├── MainActivity.kt              # نقطة الدخول + Navigation
├── WallpaperApp.kt               # Application class
├── data/
│   ├── model/Wallpaper.kt        # نموذج البيانات
│   └── repository/WallpaperRepository.kt  # مصدر البيانات (Mock حالياً)
└── ui/
    ├── theme/                    # الألوان + الخطوط + الـ Theme
    ├── components/                # GlassCard, WallpaperCard, CategoryChip
    └── screens/                   # HomeScreen, WallpaperDetailScreen
```

## 🔧 المكتبات الأساسية

| المكتبة | الاستخدام |
|---|---|
| Jetpack Compose | واجهة المستخدم |
| [Haze](https://github.com/chrisbanes/haze) | تأثير Blur/Glassmorphism حقيقي |
| Coil | تحميل وعرض الصور |
| Retrofit | (جاهز للاستخدام) ربط الـ backend على Vercel لاحقاً |
| Navigation Compose | التنقل بين الشاشات |

## 🚀 البناء عبر GitHub Actions

عند كل `push` على فرع `main`، يقوم [workflow](.github/workflows/build.yml) تلقائياً بـ:
1. تجهيز JDK 17
2. توليد Gradle Wrapper إذا لم يكن موجوداً
3. بناء `app-debug.apk`
4. رفعه كـ **Artifact** يمكن تحميله من تبويب **Actions** في الريبو

لتشغيله يدوياً أيضاً: تبويب **Actions → Build APK → Run workflow**.

## 🗺️ الخطوات القادمة (حسب الخطة الأصلية)

- [x] المرحلة 1: تطبيق خلفيات مع كتالوجات وتصنيفات
- [x] ربط الباك إند الفعلي (المستضاف على Vercel) بدل البيانات التجريبية
- [ ] المرحلة 2: معاينة الخلفية مع ساعة iOS فوقها (Depth Effect)
- [ ] المرحلة 3: صلاحية الإشعارات + تصميم إشعار تجريبي بستايل iOS
- [ ] المرحلة 4: شاشة قفل متكاملة (Accessibility Service / Window Manager)

## ⚠️ ملاحظات مهمة

- **لازم تعدّل رابط الباك اند** في `data/remote/RetrofitClient.kt` (المتغير `BASE_URL`) وتحطّه رابط مشروعك الفعلي على Vercel بعد نشره، مثلاً `https://your-project.vercel.app/`.
- لحد ما تعدّل الرابط، أو لو حصل خطأ اتصال، التطبيق بيرجع تلقائياً لبيانات Mock تجريبية (Unsplash) عشان الواجهة تفضل شغالة أثناء التطوير.
- مزايا شاشة القفل الحقيقية (Accessibility Service) تتطلب مراجعة دقيقة لسياسات Google Play قبل النشر.
- الحد الأدنى لإصدار أندرويد المدعوم: **API 26 (Android 8.0)**.
