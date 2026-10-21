# Nayem Gold Clock — Android Live Wallpaper

A lightweight Android live wallpaper with an AMOLED-black background, gold Arabic name **نايم أحمد**, date in **DDMMYYYY** format, and a live **24-hour HH:mm:ss** clock.

## Battery-conscious behavior
- Uses pure black as the background, which can save power on OLED/AMOLED screens.
- Redraws once per second only while the wallpaper is visible.
- No particles, video, shaders, network access, or continuous animation.

## Build and install
1. Open this folder in Android Studio (current stable version).
2. Allow Gradle sync and install the Android SDK platform requested by the project (API 35).
3. Select **Build > Build Bundle(s) / APK(s) > Build APK(s)**.
4. Install the resulting APK on your Android phone.
5. Long-press the home screen → **Wallpaper & style** → **Live wallpapers** → **Nayem Gold Clock** → **Set wallpaper**.

## Notes
- The time is local device time, formatted as 24-hour hours:minutes:seconds.
- The date is DDMMYYYY (for example, 21102026).
- Android launchers and manufacturers may crop wallpapers differently. The layout uses proportional sizing for portrait screens.
- This folder contains source code, not a prebuilt APK. Build it in Android Studio to create the installable APK.
