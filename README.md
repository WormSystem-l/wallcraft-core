# 🌌 Aesthetic Wallpapers

An Android app for browsing and applying **Glassmorphism** (glass + blur) wallpapers inspired by the iOS interface, built with **Kotlin + Jetpack Compose**.

This is only **Phase 1** of the full plan: a wallpaper app with catalogs, categories, and preview — without a custom lock screen or notification services yet.

---

## ✨ Current Features

- Wallpaper grid with glass-style cards
- Floating top bar with a **real Blur effect** (using the [Haze](https://github.com/chrisbanes/haze) library)
- Categories: Minimalist, Abstract, Nature, Dark Mode, Depth Effect
- Full-size preview screen + a working "Apply as Wallpaper" button via `WallpaperManager`
- Mock data ready to be replaced with a real API later

## 🧱 Technical Structure

```
app/src/main/java/com/aesthetic/wallpapers/
├── MainActivity.kt              # Entry point + Navigation
├── WallpaperApp.kt               # Application class
├── data/
│   ├── model/Wallpaper.kt        # Data model
│   └── repository/WallpaperRepository.kt  # Data source (currently Mock)
└── ui/
    ├── theme/                    # Colors + Fonts + Theme
    ├── components/                # GlassCard, WallpaperCard, CategoryChip
    └── screens/                   # HomeScreen, WallpaperDetailScreen
```

## 🔧 Core Libraries

| Library | Purpose |
|---|---|
| Jetpack Compose | UI |
| [Haze](https://github.com/chrisbanes/haze) | Real Blur/Glassmorphism effect |
| Coil | Image loading and display |
| Retrofit | (Ready to use) connecting to the Vercel backend later |
| Navigation Compose | Navigation between screens |

## 🚀 Building via GitHub Actions

On every `push` to the `main` branch, the [workflow](.github/workflows/build.yml) automatically:
1. Sets up JDK 17
2. Generates the Gradle Wrapper if it doesn't exist
3. Builds `app-debug.apk`
4. Uploads it as an **Artifact** downloadable from the repo's **Actions** tab

You can also trigger it manually: **Actions → Build APK → Run workflow**.

## 🗺️ Roadmap (per the original plan)

- [x] Phase 1: Wallpaper app with catalogs and categories
- [x] Connect the real backend (hosted on Vercel) instead of mock data
- [ ] Phase 2: Wallpaper preview with an iOS-style clock overlay (Depth Effect)
- [ ] Phase 3: Notification permissions + a sample iOS-style notification design
- [ ] Phase 4: Full lock screen integration (Accessibility Service / Window Manager)

## ⚠️ Important Notes

- **You must update the backend URL** in `data/remote/RetrofitClient.kt` (the `BASE_URL` variable) to point to your actual Vercel project URL after deployment, e.g. `https://your-project.vercel.app/`.
- Until the URL is updated, or if a connection error occurs, the app automatically falls back to mock data (Unsplash) so the UI keeps working during development.
- Real lock screen features (Accessibility Service) require careful review against Google Play policies before publishing.
- Minimum supported Android version: **API 26 (Android 8.0)**.

---

Developed by Hadi Gold — WORM SYSTEM Labs
