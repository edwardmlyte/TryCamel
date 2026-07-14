# TryCamel

[![CI](https://github.com/edwardmlyte/TryCamel/actions/workflows/ci.yml/badge.svg)](https://github.com/edwardmlyte/TryCamel/actions/workflows/ci.yml)

Share an Amazon URL to [CamelCamelCamel](https://camelcamelcamel.com/) to see a
product's price history. Adds TryCamel to the Android share sheet: from the
Amazon app (or a browser) share a product, pick TryCamel, and its
CamelCamelCamel price-history page opens.

![logo](https://raw.githubusercontent.com/edwardmlyte/TryCamel/master/art/feature_graphic.png)

## Download

Grab the latest APK from the
[**Releases**](https://github.com/edwardmlyte/TryCamel/releases) page and
sideload it onto your device.

> The APK is debug-signed for personal sideloading. You may need to allow
> installation from unknown sources.

## About this fork

This is a modernized rewrite of the original
[cstew/TryCamel](https://github.com/cstew/TryCamel) (unmaintained since 2018).
The app was rebuilt to install and run on current Android:

- Rewritten in **Kotlin** on **AndroidX** with ViewBinding and ViewPager2
- Updated build stack: Android Gradle Plugin 8.13, Gradle 8.13, `targetSdk` 36
- Removed dead dependencies (JCenter, Fabric/Crashlytics)
- HTTPS CamelCamelCamel endpoints
- Unit tests run on the JVM via Robolectric; CI builds/verifies every push

## Building

```sh
./gradlew assembleDebug        # build debug APK -> app/build/outputs/apk/debug/
./gradlew testDebugUnitTest    # run unit tests
./gradlew lintDebug            # run Android lint
```

Requires JDK 21 and an Android SDK (install via Android Studio). For terminal
builds, point Gradle at the SDK, e.g.:

```sh
export ANDROID_HOME="$HOME/Library/Android/sdk"
export PATH="$ANDROID_HOME/platform-tools:$PATH"
```

[Screenshots](https://github.com/edwardmlyte/TryCamel/tree/master/art)

## License

MIT. Original work Copyright (c) 2015 Chris Stewart; modernization
Copyright (c) 2026 Edward Maxwell-Lyte. See [LICENSE](LICENSE).
