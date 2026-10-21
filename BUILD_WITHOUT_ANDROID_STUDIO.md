# Build the APK without Android Studio

This project includes a GitHub Actions workflow that builds the Android APK in GitHub's cloud build runner.

1. Create a new GitHub repository (for example `NayemLiveWallpaper`).
2. Upload the **contents** of this folder to the repository root. Make sure `.github/workflows/build-apk.yml` is included. If uploading through the GitHub website, enable viewing hidden files or upload the `.github` folder as well.
3. Open the repository's **Actions** tab and select **Build Nayem Gold Clock APK**.
4. Click **Run workflow** if it has not started automatically after your upload.
5. Wait for the workflow to finish successfully. Open the completed run and download the `Nayem-Gold-Clock-debug-apk` artifact.
6. Extract the downloaded artifact ZIP on your phone and install `app-debug.apk`. Android may ask you to allow installation from your browser or file manager; only proceed if you uploaded this source yourself and trust it.
7. Long-press the home screen → Wallpapers / Wallpaper & style → Live wallpapers → Nayem Gold Clock.

This creates a **debug APK** for personal testing, not a Play Store release. The APK is only available after GitHub Actions successfully builds it. A GitHub account and internet access are required; Android Studio is not required.
